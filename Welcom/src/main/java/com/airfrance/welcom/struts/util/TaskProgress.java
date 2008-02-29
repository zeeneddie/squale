package com.airfrance.welcom.struts.util;

/**
 * Third-party code used for this class. 
 * Impossible to find original licence though.
 * Copyright left to original author if ever found in the future.
 */

public class TaskProgress {
	/**
	 * Current value of the progress.
	 */
	private int progress;

	/**
	 * Total length of the task.
	 */
	private int length;

	/**
	 * Timestamp. Used to remove old tasks.
	 */
	private long creationDate;

	/**
	 * Constructor
	 * @param id taskID
	 */
	public TaskProgress() {
		this.length = -1;
		this.progress = 0;
		creationDate = System.currentTimeMillis();
	}

	/**
	 * Get the percentage of work complete.
	 * @return % of work complete
	 */
	public int getPercentComplete() {
		int result = 0;
		if (length > -1)
			result = (int) (100 * ((double) (progress) / (double) length));

		if (result > 100)
			result = 100;

		return result;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void complete() {
		this.progress = length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int i) {
		length = i;
	}

	public String toString() {
		return progress + "/" + length;
	}

}
