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
    
}
