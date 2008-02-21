package com.airfrance.welcom.struts.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * Batch task management.
 * 
 * @author 6361371
 * 
 */
public class WatchedTaskManager {


	/** logger */
	private static Log log = LogFactory.getLog(WatchedTaskManager.class);

	private static final int DEFAULT_THREAD_POOL_SIZE = 10;

	/**
	 * Maximum amount of time allowed for a task execution. After this delay the
	 * tasks are removed by calling the clean() method.
	 */
	private static int MAX_TASK_DURATION;

	private static final int DEFAULT_THREAD_LIFE = 60000;

	/**
	 * Information on all the tasks currently running.
	 */
	private Hashtable activeTasks = new Hashtable();

	/**
	 * Last active task.
	 */
	private int taskIdCounter = Integer.MIN_VALUE;

	/**
	 * All possible values have been used at least once
	 */
	private boolean taskIdRecyclingMode = false;

	private Timer gcTimer;

	/**
	 * Thread pool to execute batches.
	 */
	private WorkQueue workQueue;

	private final class GCTask extends TimerTask {
			WatchedTaskManager mgr;

			public void run() {
				log.debug("Task clean launch from GC");
				mgr.clean();
			}

			/**
			 * @param mgr
			 */
			public GCTask(WatchedTaskManager mgr) {
				this.mgr = mgr;
			}
		}


	private void init() {
		gcTimer = new Timer(true);
		
		int poolSize = 0;
		try {
			String strPoolSize =
				WelcomConfigurator.getMessage(
					WelcomConfigurator.BATCH_TASKS_POOLSIZE);
			poolSize = Integer.parseInt(strPoolSize);
		} catch (Exception e) {
			log.error(
				"Bad format for parameter "
					+ WelcomConfigurator.BATCH_TASKS_POOLSIZE,
				e);
			poolSize = DEFAULT_THREAD_POOL_SIZE;
		}
		workQueue = new WorkQueue(poolSize);

		// ----------
		int taskDuration = 0;
		try {
			String strLife =
				WelcomConfigurator.getMessage(
					WelcomConfigurator.BATCH_TASKS_MAXLIFEDURATION);
			taskDuration = Integer.parseInt(strLife);
		} catch (Exception e) {
			log.error(
				"Bad format for parameter "
					+ WelcomConfigurator.BATCH_TASKS_MAXLIFEDURATION,
				e);
			taskDuration = DEFAULT_THREAD_LIFE;
		}
		MAX_TASK_DURATION = taskDuration * 1000;
	}

	/**
	 * Initialize a new task progress data structure.
	 * 
	 * @return id to use to retrieve/update informations on task progress
	 */
	public synchronized Object regTask(WatchedTask task) {
		log.debug("Task " + task + "registred");
		int taskId = getNextId();
		TaskProgress progress = new TaskProgress();
		task.setProgress(progress);

		activeTasks.put(new Integer(taskId), task);

		GCTask tacheGC = new GCTask(this);
		gcTimer.schedule(tacheGC, MAX_TASK_DURATION / 2);

		return new Integer(taskId);
	}

	/**
	 * Generate an id for the TaskProgress
	 * 
	 * @return ID
	 */
	private int getNextId() {
		if ((taskIdCounter < Integer.MAX_VALUE)
			&& (taskIdRecyclingMode == false)) {
			return taskIdCounter++;
		} else {
			// All Long value have been used at least 1 time
			// Start again from beginning.
			if (taskIdCounter < Integer.MAX_VALUE) {
				taskIdCounter = Integer.MIN_VALUE;
			}

			while (activeTasks.containsKey(new Integer(taskIdCounter))) {
				taskIdCounter++;
			}

			return taskIdCounter;
		}
	}

	/**
	 * Retrieve percentage of work complete.
	 * 
	 * @param taskId
	 * @return % of work complete
	 */
	public int getTaskProgressPct(Object taskId) {
		WatchedTask batch = (WatchedTask) activeTasks.get(taskId);
		if (batch != null) {
			return batch.getProgress().getPercentComplete();
		} else {
			return -1;
		}

	}

	/**
	 * GET Accessor.
	 * 
	 * @param taskId
	 * @return
	 */
	public WatchedTask getTask(Object taskId) {
		return (WatchedTask) activeTasks.get(new Integer(taskId.toString()));
	}

	/**
	 * GET Accessor.
	 * 
	 * @param taskId
	 * @return
	 */
	public Object getTaskId(WatchedTask task) {
		if (task == null) {
			return null;
		}

		Set entries = activeTasks.entrySet();
		Iterator iter = entries.iterator();

		while (iter.hasNext()) {
			Entry e = (Entry) iter.next();
			if (task.equals(e.getValue())) {
				return e.getKey();
			}
		}

		return null;
	}

	/**
	 * GET Accessor.
	 * 
	 * @param taskId
	 * @return
	 */
	public Collection getAllTasks() {
		return activeTasks.values();
	}

	/**
	 * Remove task information data.
	 * 
	 * @param taskId
	 */
	private void removeTask(Object taskId) {
		activeTasks.remove(new Integer(taskId.toString()));
		clean();
	}

	/**
	 * Remove all "olds" tasks information in case of a process anormal end.
	 */
	public void clean() {
		log.info("Task cleaning");
		Iterator iter = activeTasks.values().iterator();
		long now = System.currentTimeMillis();
		while (iter.hasNext()) {
			WatchedTask element = (WatchedTask) iter.next();

			long creationDate = element.getProgress().getCreationDate();

			// After 10 minutes, a task is removed from the pool
			if ((now - creationDate) > MAX_TASK_DURATION) {
				iter.remove();
			}

		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		gcTimer.cancel();
		gcTimer = null;
	}

	/**
	 * GET Accessor.
	 * 
	 * @return Work queue
	 */
	public WorkQueue getWorkQueue() {
		return workQueue;
	}

	/**
	 * 
	 * @param id
	 */
	public synchronized void killTask(Object id) {
		WatchedTask batch =
			(WatchedTask) activeTasks.get(new Integer(id.toString()));

		if (batch != null) {
			log.info("killing task " + id);
			removeTask(id);
			getWorkQueue().stopTask(batch);
		}
	}

	public static synchronized WatchedTaskManager getInstance(HttpServletRequest request) {

		WatchedTaskManager instance =
			(WatchedTaskManager) request.getSession().getAttribute(
				WatchedTaskManager.class.getName());

		if (instance == null) {
			instance = new WatchedTaskManager();
			instance.init();
			
			log.info("TaskMgr registred in session");
			request.getSession().setAttribute(
							WatchedTaskManager.class.getName(), instance);
		}

		return instance;
	}


}
