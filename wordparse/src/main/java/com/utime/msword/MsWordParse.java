package com.utime.msword;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;

import com.utime.msword.define.EDocAlignment;
import com.utime.msword.define.EDocPictureType;
import com.utime.msword.objects.DocElement;
import com.utime.msword.objects.DocFont;
import com.utime.msword.objects.DocImage;
import com.utime.msword.objects.DocObject;
import com.utime.msword.objects.DocTable;
import com.utime.msword.objects.DocText;
import com.utime.msword.utils.DocNumLevelListMgr;

/**
 * MSWord document analysis.
 */
public class MsWordParse {

	/**
	 * A list of analyzed information.
	 */
    final List<DocElement> docList = new ArrayList<>();

	/**
	 * Document number level management
	 */
	final DocNumLevelListMgr numLevelListMgr = new DocNumLevelListMgr();

    public MsWordParse(String fileName ) throws FileNotFoundException, IOException{
        this(new File( fileName) );
    }

    public MsWordParse(File file ) throws FileNotFoundException, IOException{
        final XWPFDocument doc = new XWPFDocument( new FileInputStream(file) );
		
		final Iterator<IBodyElement> itBody = doc.getBodyElementsIterator();
		
		this.procBodyElement(this.docList, itBody);
		
		doc.close();
    }

	public List<DocElement> getDocList() {
		return docList;
	}
    
	/**
	 * MSWord document content analysis.
	 * @param docList 
	 * @param para
	 */
	private void procXWPFParagraph(List<DocElement> docList, XWPFParagraph para ) {

		final DocElement docElement = new DocElement();
		docList.add(docElement);
		
		if( para.isPageBreak() ) {
			docElement.setPageBreak( true);
			return;
		}
		
		switch( para.getAlignment()) {
		case CENTER: docElement.setAlignment( EDocAlignment.CENTER ); break;
		case RIGHT: docElement.setAlignment( EDocAlignment.RIGHT ); break;
		default : docElement.setAlignment( EDocAlignment.LEFT ); break;
		}
		
		boolean isFirst = true;
		boolean debug = false;
		final List<XWPFRun> iruns = para.getRuns();
		if( iruns.size() > 0 ) {
			docElement.setEmpty( false );
			final List<DocObject> items = new ArrayList<>();
			docElement.setContents( items );
			
			Boolean bold = null, italic = null;
			Double size = null; String color = null;
			
			DocObject item = null;
	        for (IRunElement run : iruns) {
	            if (run instanceof XWPFRun) {
	                final XWPFRun xRun = (XWPFRun) run;
	                
	                if( xRun.getCTR().getBrArray().length > 0 ) {
	                	// page break!
	                	docElement.setPageBreak(true);
	    				continue;
	                }

	                if( debug ) {
	                	debug = false;
	                }
	                
	                final List<XWPFPicture> pictures = xRun.getEmbeddedPictures();
	                if( pictures != null && pictures.size() > 0 ) {
	                	for( XWPFPicture picture : pictures ) {
	                		
							final XWPFPictureData pictureData = picture.getPictureData();
	                		String picName = picture.getDescription();
	                		if( picName == null || picName.length() < 1 ) {
	                			picName = picture.getCTPicture().getNvPicPr().getCNvPr().getName();
	                		}
	                		
	                		if( picName == null || picName.length() < 1 ) {
	                			picName = pictureData.getFileName();
	                		}

	                		item = new DocImage();
	                		final DocImage docImage = (DocImage)item;
	                		docImage.setName( picName );
	                		docImage.setWidth( picture.getWidth() );
							docImage.setPictureType( EDocPictureType.valueOf( pictureData.getPictureTypeEnum().name() ) );
	                		docImage.setData( Base64.getEncoder().encodeToString( pictureData.getData() ) );
	                		
	                		items.add( item );
	                		
	                		bold = null; italic = null; size = null; color = null;
	                	}

	                } else {
	                	int same = 0;
	                	if( bold != null && bold == xRun.isBold() ) {
	                		same++;
	                	}else {
	                		bold = xRun.isBold();
	                	}
	                	
	                	if( italic != null && italic == xRun.isBold() ) {
	                		same++;
	                	}else {
	                		italic = xRun.isItalic();
	                	}
	                	
	                	if( size != null && null != xRun.getFontSizeAsDouble() && size.equals(xRun.getFontSizeAsDouble())) {
	                		same++;
	                	}else if( size == null && null == xRun.getFontSizeAsDouble()){
	                		same++;
	                	} else {
	                		size = xRun.getFontSizeAsDouble();
	                	}
	                	
	                	if( color != null && null != xRun.getColor() && color.equals(xRun.getColor())) {
	                		same++;
	                	}else if( color == null && null == xRun.getColor() ){
	                		same++;
	                	} else {
	                		color = xRun.getColor();
	                	}
	                	
	                	if( same != 4 ) {
	                		
	                		item = new DocText();
	                		final DocText docText = (DocText)item;
							final DocFont font = new DocFont();
	                		docText.setFont( font );
	                		font.setBold( bold );
	                		font.setItalic( italic );
	                		font.setSize( size );
	                		font.setColor( color );
	                		
	                		items.add( item );
	                		
	                	} else {

	                		if( item == null ) {
	                			item = new DocText();
	                		}
	                	}
	                	
	                	if( isFirst ) {
							final DocText txt = (DocText)item;
	                		txt.setText( this.getFrontNum(para) );
	                		isFirst = false;
	                	}

		                if (xRun.getCTR().getDelTextArray().length == 0) {
							final DocText txt = (DocText)item;
							txt.setText( txt.getText() + xRun.toString() );
		                }
	                }
	                
	            } else if (run instanceof XWPFSDT) {
            		if( item == null ) {
            			item = new DocText();
            		}

					final DocText txt = (DocText)item;
					txt.setText( txt.getText() + ((XWPFSDT) run).getContent().getText() );
	            } else {
            		if( item == null ) {
            			item = new DocText();
            		}

					final DocText txt = (DocText)item;
					txt.setText( run.toString() );
	            }
	        }
	        
	    } else {
	    	docElement.setEmpty( true );
	    }
	}

	/**
	 * Body analysis.
	 * @param docList
	 * @param itBody
	 */
    private void procBodyElement(List<DocElement> docList, Iterator<IBodyElement> itBody ) {
		while (itBody.hasNext()) {
			final IBodyElement body = itBody.next();
			
			switch( body.getElementType() ) {
			case CONTENTCONTROL:{
				System.out.println("Not support.");
				break;
			}
			case PARAGRAPH:{
				final XWPFParagraph paragraph = (XWPFParagraph) body;
				this.procXWPFParagraph( docList, paragraph );
				break;
			}
			case TABLE:{
				final XWPFTable table = (XWPFTable) body;
				this.procXWPFTable( docList, table );
				break;
			}
			}
		}
	}
	
	/**
	 * Table analysis.
	 * @param docList
	 * @param table
	 */
	private void procXWPFTable(List<DocElement> docList, XWPFTable table) {
		
		final DocElement docElement = new DocElement();
		docList.add(docElement);
		
		final TableRowAlign rowAlign = table.getTableAlignment();
		if( rowAlign != null ) {
			switch( rowAlign ) {
			case CENTER: docElement.setAlignment( EDocAlignment.CENTER ); break;
			case RIGHT: docElement.setAlignment( EDocAlignment.RIGHT ); break;
			default : docElement.setAlignment( EDocAlignment.LEFT ); break;
			}
		}else {
			docElement.setAlignment( EDocAlignment.LEFT );
		}
		
		final List<DocObject> contents = new ArrayList<>();
	    docElement.setContents( contents );

		final DocObject item = new DocTable();
	    contents.add(item);

		final DocTable docTable = (DocTable)item;

		final CTTblPr tblPr = table.getCTTbl().getTblPr();
	    docTable.setWidth( ((BigInteger)tblPr.getTblW().getW()).intValue() );
	    
	    
	    // Get the rows in the table
	    final List<XWPFTableRow> rows = table.getRows();

	    {
	    	// Retrieves the width of the table.
		    final List<Integer> cellWidthList = docTable.getCellWidthList(); 
		    for (XWPFTableRow row : rows) {
		    	final List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					final Integer width = ((BigInteger)cell.getCTTc().getTcPr().getTcW().getW()).intValue();
					cellWidthList.add(width);
				}
		    	break;
		    }
	    }
	    
	    // Iterate over the rows
	    {
		    final List<List<DocElement>> rowList = docTable.getRows();
		    for (XWPFTableRow row : rows) {
		    	
				// Get the cells in the row
				final List<XWPFTableCell> cells = row.getTableCells();
				final List<DocElement> cellList = new ArrayList<>();
				rowList.add(cellList);
				
				// Iterate over the cells
				// The table contents are document properties again, so read the Body.
				for (XWPFTableCell cell : cells) {
					
					final List<IBodyElement> lst = cell.getBodyElements();
					this.procBodyElement(cellList, lst.iterator() );
				}
		    }
	    }
	}

	/**
	 * Character creation according to level.
	 * @param para
	 * @return
	 */
	private String getFrontNum( XWPFParagraph para ) {
		String result = "";
		if( para.getNumIlvl() == null ) {
			return result;
		}
		
		// paragraph ID
		final BigInteger numID = para.getNumID();
		
		if( ! numID.equals( numLevelListMgr.getNumID() ) ) {
			numLevelListMgr.clear();
			numLevelListMgr.setNumID( numID );
		}

		result = numLevelListMgr.getLevel(para.getNumFmt(), para.getNumLevelText(), para.getNumIlvl());
		
		return result;
	}
}
