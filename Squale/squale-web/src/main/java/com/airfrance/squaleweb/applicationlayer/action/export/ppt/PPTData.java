package com.airfrance.squaleweb.applicationlayer.action.export.ppt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Line;
import org.apache.poi.hslf.model.MasterSheet;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.Table;
import org.apache.poi.hslf.model.TableCell;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.airfrance.squalecommon.util.xml.ParsingHandler;
import com.airfrance.squalecommon.util.xml.XmlResolver;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 * General data to create ppt documents
 */
public class PPTData
{

    /** DTD location */
    public static final String DTD_LOCATION = "/com/airfrance/squaleweb/resources/ppt/dtd/ppt_mapping-1.0.dtd";

    /** Public id of mapping file */
    public static final String PUBLIC_ID = "-//Squale//DTD PPT Mapping 1.0//EN";

    /** Default font name for text box in ppt */
    protected static final String DEFAULT_FONT_NAME = "Arial";

    /** Default font size for text box in ppt */
    protected static final int DEFAULT_FONT_SIZE = 10;

    /** Default font color for text box in ppt */
    protected static final Color DEFAULT_FONT_COLOR = Color.BLACK;

    /** Default backgroud color for cell in ppt */
    protected static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    /** Log */
    private static Log LOGGER = LogFactory.getLog( PPTData.class );

    /** Mapping indicating settings */
    protected Document mapping;

    /** Presentation to modify */
    protected SlideShow presentation;

    /** Slides of model to use */
    protected Slide[] modelSlides;

    /** request */
    protected HttpServletRequest request;

    /** Errors while parsing mapping file */
    protected StringBuffer errors = new StringBuffer();

    /**
     * Getter for presentation attribute
     * 
     * @return presentation
     */
    public SlideShow getPresentation()
    {
        return presentation;
    }

    /**
     * Create a document with mapping file
     * 
     * @param mappingStream stream of mapping file
     * @throws PPTGeneratorException if error while parsing mapping file
     */
    public void setMapping( InputStream mappingStream )
        throws PPTGeneratorException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating( true );
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver( new XmlResolver( PUBLIC_ID, DTD_LOCATION ) );
            db.setErrorHandler( new ParsingHandler( LOGGER, errors ) );
            mapping = db.parse( mappingStream );
            if ( errors.length() > 0 )
            {
                handleException( "export.audit_report.mapping.error", new String[] { errors.toString() } );
            }
        }
        catch ( ParserConfigurationException e )
        {
            handleException( "export.audit_report.mapping.error", new String[] { e.getMessage() } );
        }
        catch ( SAXException e )
        {
            handleException( "export.audit_report.mapping.error", new String[] { e.getMessage() } );
        }
        catch ( IOException e )
        {
            handleException( "export.audit_report.mapping.error", new String[] { e.getMessage() } );
        }
    }

    /**
     * Set the presentation attribute
     * 
     * @param pPresentationStream new presentation
     * @throws PPTGeneratorException if error creating ppt
     */
    public void setPresentation( InputStream pPresentationStream )
        throws PPTGeneratorException
    {
        try
        {
            this.presentation = new SlideShow( pPresentationStream );
        }
        catch ( IOException ioe )
        {
            handleException( "export.audit_report.presentation.error.format" );
        }
    }

    /**
     * Getter for modelSlides attribute
     * 
     * @return slides of model
     */
    public Slide[] getModelSlides()
    {
        return modelSlides;
    }

    /**
     * Set modelSlides attribute
     * 
     * @param pModelStream the model
     * @throws PPTGeneratorException if error occured while getting slides
     */
    public void setModel( InputStream pModelStream )
        throws PPTGeneratorException
    {
        try
        {
            SlideShow model = new SlideShow( new HSLFSlideShow( pModelStream ) );
            modelSlides = model.getSlides();
        }
        catch ( IOException ioe )
        {
            handleException( "export.audit_report.model.error.format" );
        }
    }

    /**
     * Launched an PPTGeneratorException
     * 
     * @param pKey key for exception message
     * @throws PPTGeneratorException error launched
     */
    private void handleException( String pKey )
        throws PPTGeneratorException
    {
        throw new PPTGeneratorException( WebMessages.getString( request, pKey ) );
    }

    /**
     * Launched an PPTGeneratorException
     * 
     * @param pKey key for exception message
     * @param pArgs message parameters
     * @throws PPTGeneratorException error launched
     */
    private void handleException( String pKey, String[] pArgs )
        throws PPTGeneratorException
    {
        throw new PPTGeneratorException( (String) WebMessages.getString( request.getLocale(), pKey, pArgs ) );
    }

    /**
     * Modify the presentation in function of mapping description
     * 
     * @throws PPTGeneratorException if error occured while generating ppt
     */
    public void modifyPresentation()
        throws PPTGeneratorException
    {
        // Parse mapping
        // Get class name with root tag
        Element root = mapping.getDocumentElement();
        // Get class name
        String classname = root.getAttribute( "class" );
        Class dataClass = null;
        try
        {
            dataClass = Class.forName( classname );
        }
        catch ( ClassNotFoundException e )
        {
            throw new PPTGeneratorException( "class not found :" + classname );
        }
        // initalization
        Class[] argsForSet = new Class[] { Slide.class, Rectangle.class };
        Class[] argsForAdd = new Class[] { MasterSheet.class };
        NodeList nl = root.getElementsByTagName( "*" );

        for ( int i = 0; i < nl.getLength(); i++ )
        {
            Node curChild = nl.item( i );
            String methodName = ( (Element) curChild ).getAttribute( "methodName" );
            if ( curChild.getNodeName().equals( "add" ) )
            {
                int modelPage = Integer.parseInt( ( (Element) curChild ).getAttribute( "model" ) );
                addSlide( getMethod( dataClass, methodName, argsForAdd ), modelPage );
            }
            else if ( curChild.getNodeName().equals( "set" ) )
            {
                int slide = Integer.parseInt( ( (Element) curChild ).getAttribute( "slide" ) );
                String where = ( (Element) curChild ).getAttribute( "where" );
                setSlide( getMethod( dataClass, methodName, argsForSet ), where, slide );
            }

        }
    }

    /**
     * Get a method
     * 
     * @param dataClass utility class
     * @param methodName method to find
     * @param args method's arguments
     * @return corresponding method
     * @throws PPTGeneratorException if error
     */
    private Method getMethod( Class dataClass, String methodName, Class[] args )
        throws PPTGeneratorException
    {
        Method methodToCall = null;
        try
        {
            methodToCall = dataClass.getMethod( methodName, args );
        }
        catch ( SecurityException e )
        {
            handleException( "export.audit_report.mapping.error.method.access", new String[] { methodName } );
        }
        catch ( NoSuchMethodException e )
        {
            handleException( "export.audit_report.mapping.error.method.not_found", new String[] { methodName } );
        }
        return methodToCall;
    }

    /**
     * Add slide at the end of the presentation
     * 
     * @param methodToCall method to invoke to create slide
     * @param modelPage model to use
     * @throws PPTGeneratorException if error
     */
    private void addSlide( Method methodToCall, int modelPage )
        throws PPTGeneratorException
    {
        if ( modelPage > modelSlides.length || modelPage < 1 )
        {
            handleException( "export.audit_report.mapping.error.model_slide", new String[] { "" + modelPage } );
        }
        try
        {
            methodToCall.invoke( this, new Object[] { modelSlides[modelPage - 1].getMasterSheet() } );
        }
        catch ( IllegalArgumentException e )
        {
            handleException( "export.audit_report.mapping.error.method_args", new String[] { methodToCall.getName() } );
        }
        catch ( IllegalAccessException e )
        {
            handleException( "export.audit_report.mapping.error.method.access", new String[] { methodToCall.getName() } );
        }
        catch ( InvocationTargetException e )
        {
            handleException( "export.audit_report.mapping.error.method_exception", new String[] {
                methodToCall.getName(), e.getMessage() } );
        }
    }

    /**
     * Set a slide
     * 
     * @param methodToCall method to invoke to create slide
     * @param where reference for setting
     * @param slide page number of slide to change
     * @throws PPTGeneratorException if error
     */
    private void setSlide( Method methodToCall, String where, int slide )
        throws PPTGeneratorException
    {
        if ( slide > presentation.getSlides().length || slide < 1 )
        {
            handleException( "export.audit_report.mapping.error.presentation_slide", new String[] { "" + slide } );
        }
        try
        {
            Slide slideToSet = getPresentation().getSlides()[slide - 1];
            Rectangle whereRec = new Rectangle();
            for ( int i = 0; i < slideToSet.getShapes().length; i++ )
            {
                if ( slideToSet.getShapes()[i] instanceof TextBox
                    && ( (TextBox) slideToSet.getShapes()[i] ).getText() != null
                    && where.equalsIgnoreCase( ( (TextBox) slideToSet.getShapes()[i] ).getText().trim() ) )
                {
                    whereRec = slideToSet.getShapes()[i].getAnchor();

                }
            }
            methodToCall.invoke( this, new Object[] { slideToSet, whereRec } );
        }
        catch ( IllegalArgumentException e )
        {
            handleException( "export.audit_report.mapping.error.method_args", new String[] { methodToCall.getName() } );
        }
        catch ( IllegalAccessException e )
        {
            handleException( "export.audit_report.mapping.error.method.access", new String[] { methodToCall.getName() } );
        }
        catch ( InvocationTargetException e )
        {
            handleException( "export.audit_report.mapping.error.method_exception", new String[] {
                methodToCall.getName(), e.getMessage() } );
        }
    }

    /**
     * Getter for errors propertie
     * 
     * @return errors occured while parsing mapping file
     */
    public StringBuffer getErrors()
    {
        return errors;
    }

    /**
     * Set properties of cell
     * 
     * @param cell cell to set
     * @param text text of cell
     */
    protected void setTabCell( TableCell cell, String text )
    {
        setTabCell( cell, text, DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, DEFAULT_BACKGROUND_COLOR );
    }

    /**
     * Set properties of a cell
     * 
     * @param cell cell to set
     * @param text text of cell
     * @param fontName font name for text
     * @param fontSize font name for text
     * @param backgroundColor background color for cell
     */
    protected void setTabCell( TableCell cell, String text, String fontName, int fontSize, Color backgroundColor )
    {
        setTabCell( cell, text, fontName, fontSize );
        cell.setFillColor( backgroundColor );
    }

    /**
     * Set properties of a cell
     * 
     * @param cell cell to set
     * @param text text of cell
     * @param fontName font name for text
     * @param fontSize font name for text
     * @param bold if text in bold
     * @param fontColor color of text
     * @param backgroundColor background color for cell
     */
    protected void setTabCell( TableCell cell, String text, String fontName, int fontSize, Color backgroundColor,
                               boolean bold, Color fontColor )
    {
        setTabCell( cell, text, fontName, fontSize, bold, fontColor );
        cell.setFillColor( backgroundColor );
    }

    /**
     * Set properties of a cell
     * 
     * @param cell cell to set
     * @param text text of cell
     * @param fontName font name for text
     * @param fontSize font name for text
     */
    protected void setTabCell( TableCell cell, String text, String fontName, int fontSize )
    {
        cell.setText( text );
        RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
        rt.setFontName( fontName );
        rt.setFontSize( fontSize );
        cell.setVerticalAlignment( TextBox.AnchorMiddle );
        cell.setMarginBottom( 0 );
        cell.setMarginTop( 0 );
    }

    /**
     * Set properties of a cell
     * 
     * @param cell cell to set
     * @param text text of cell
     * @param fontName font name for text
     * @param fontSize font name for text
     * @param bold if text in bold
     * @param fontColor color of text
     */
    protected void setTabCell( TableCell cell, String text, String fontName, int fontSize, boolean bold, Color fontColor )
    {
        cell.setText( text );
        RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
        rt.setFontName( fontName );
        rt.setFontSize( fontSize );
        rt.setBold( bold );
        rt.setFontColor( fontColor );
        cell.setVerticalAlignment( TextBox.AnchorMiddle );
    }

    /**
     * Create a table with black border
     * 
     * @param nbRows number of rows
     * @param nbCol number of columns
     * @return the table
     */
    protected Table createTableWithBorder( int nbRows, int nbCol )
    {
        final int columnHeight = 10;
        Table table = new Table( nbRows, nbCol );
        // set table borders
        Line border = table.createBorder();
        border.setLineColor( Color.black );
        border.setLineWidth( 1.0 );
        table.setAllBorders( border );
        // minimize height for all row
        for(int i=0; i< nbRows; i++) {
            table.setRowHeight( i, columnHeight );
        }
        return table;
    }

    /**
     * Add a text box in the slide
     * 
     * @param slide slide to set
     * @param text text to add
     * @param fontSize font size of the text
     * @param anchor place of text box
     * @param isBullet if text is a list
     */
    protected void addTextBox( Slide slide, String text, int fontSize, Rectangle anchor, boolean isBullet )
    {
        TextBox txt = new TextBox();
        txt.setText( text );
        txt.setAnchor( anchor );
        RichTextRun rt = txt.getTextRun().getRichTextRuns()[0];
        rt.setFontSize( fontSize );
        rt.setBullet( isBullet );
        slide.addShape( txt );
    }

    /**
     * Add title in slide
     * 
     * @param slide slide to set
     * @param title text of title
     */
    protected void addTitle( Slide slide, String title )
    {
        TextBox titleBox = slide.addTitle();
        titleBox.setText( title );
    }

    /**
     * Add title in slide
     * 
     * @param slide slide to set
     * @param title text of title
     * @param fontSize size for font
     */
    protected void addTitle( Slide slide, String title, int fontSize )
    {
        TextBox titleBox = slide.addTitle();
        titleBox.setText( title );
        RichTextRun rt = titleBox.getTextRun().getRichTextRuns()[0];
        rt.setFontSize( fontSize );
    }

    /**
     * Add graph in slide
     * 
     * @param slideToSet slide to set
     * @param graph graph to add
     * @param anchor place to add graph
     * @throws IOException if error
     */
    protected void addJFreeChart( Slide slideToSet, JFreeChart graph, Rectangle anchor )
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int width = anchor.width;
        int height = anchor.height;
        if ( width <= 0 )
        {
            width = getPresentation().getPageSize().width;
        }
        if ( height <= 0 )
        {
            height = getPresentation().getPageSize().height;
        }
        ChartUtilities.writeChartAsPNG( out, graph, width, height );
        addPicture( slideToSet, out.toByteArray(), anchor );
    }

    /**
     * Add a picture in a slide
     * 
     * @param slideToSet slide to set
     * @param data image data
     * @param anchor place to add image
     * @throws IOException if error
     */
    protected void addPicture( Slide slideToSet, byte[] data, Rectangle anchor )
        throws IOException
    {
        int idx = getPresentation().addPicture( data, Picture.PNG );
        Picture pict = new Picture( idx );
        pict.setAnchor( anchor );
        slideToSet.addShape( pict );
    }
    
    /**
     * Convert a html code to an image
     * 
     * @param html html to convert
     * @return html converted to png
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    protected byte[] htmlToImage( String html )
        throws IOException, PPTGeneratorException
    {
    	try {
    		JEditorPane editor = new JEditorPane();
	        editor.setContentType( "text/html" );
	        editor.setText( html );
	        editor.setSize(editor.getPreferredSize());
	        editor.addNotify();
	        LOGGER.debug("Panel is built");
	        BufferedImage bufferSave =
	            new BufferedImage( editor.getPreferredSize().width, editor.getPreferredSize().height,
	                               BufferedImage.TYPE_3BYTE_BGR );
	        Graphics g = bufferSave.getGraphics();
	        g.setColor( Color.WHITE );
	        g.fillRect( 0, 0, editor.getPreferredSize().width, editor.getPreferredSize().height );
	        editor.paint( g );
	        LOGGER.debug("graphics is drawn");
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        ImageIO.write( bufferSave, "png", out );
	        return out.toByteArray();
    	} catch ( HeadlessException e ) {
    		LOGGER.error("X Server no initialized or -Djava.awt.headless=true not set !");
    		throw new PPTGeneratorException("X Server no initialized or -Djava.awt.headless=true not set !");
    	}
    }
    
    /**
     * Add an image with a html code without change its dimension
     * 
     * @param slideToSet slide to set
     * @param html html code
     * @param x horizontal position
     * @param y vertical position
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    protected void addHtmlPicture( Slide slideToSet, String html, int x, int y ) throws IOException, PPTGeneratorException
    {
    	try {
    		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
    		if ( ! ge.isHeadlessInstance() ) {
    			LOGGER.warn("Runtime is not configured for supporting graphiv manipulation !");
    		}
    		JEditorPane editor = new JEditorPane();
	        editor.setContentType( "text/html" );
	        editor.setText( html );
	        LOGGER.debug("Editor pane is built");
	        editor.setSize(editor.getPreferredSize());
	        editor.addNotify(); // Serveur X requis
	        LOGGER.debug("Panel rendering is done");
	        BufferedImage bufferSave =
	            new BufferedImage( editor.getPreferredSize().width, editor.getPreferredSize().height,
	                               BufferedImage.TYPE_3BYTE_BGR );
	        Graphics g = bufferSave.getGraphics();
	        g.setColor( Color.WHITE );
	        g.fillRect( 0, 0, editor.getPreferredSize().width, editor.getPreferredSize().height );
	        editor.paint( g );
	        LOGGER.debug("graphics is drawn");
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        ImageIO.write( bufferSave, "png", out );
	        LOGGER.debug("image is written");
	        addPicture( slideToSet, out.toByteArray(), new Rectangle(x, y, editor.getPreferredSize().width, editor.getPreferredSize().height) );
	        LOGGER.debug("image is added");
    	} catch ( HeadlessException e ) {
    		LOGGER.error("X Server no initialized or -Djava.awt.headless=true not set !");
    		throw new PPTGeneratorException("X Server no initialized or -Djava.awt.headless=true not set !");
    	}
    }

    private BufferedImage convertImgToBufferedImg(Image limage, String l)
    {
        if(limage instanceof BufferedImage)
        {
            return((BufferedImage)limage);
        }
        else
        {
            Image lImage = new ImageIcon(limage).getImage();
            BufferedImage bufferedimage = new BufferedImage(lImage.getWidth(null),
                                                            lImage.getHeight(null),
                                                            BufferedImage.TYPE_INT_RGB);
            Graphics gr = bufferedimage.createGraphics();
            gr.drawImage(lImage,0,0,null);
            gr.dispose();
            return(bufferedimage);
        }
    }
}
