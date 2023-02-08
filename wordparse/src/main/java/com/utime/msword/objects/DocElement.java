package com.utime.msword.objects;

import java.io.Serializable;
import java.util.List;

import com.utime.msword.define.EDocAlignment;

import lombok.Data;

@Data
public class DocElement implements Serializable{
		
    private static final long serialVersionUID = -3860334621067734556L;
    
    /** 
     * Separation of pagination.
     * true : page break
     */
    boolean pageBreak;

    /**
     * empty.
     * single line break.
     */
    boolean empty;
    /**
     * Document Alignment
     */
    EDocAlignment alignment;

    /**
     * Documents contents.
     */
    List<DocObject> contents;

    @Override
		public String toString() {
			final String result;
			if( this.pageBreak ) {
				result = "PageBreak";
			}else if( this.empty ) {
				result = "Empty";
			}else {
				result = "DocElement [alignment=" + alignment + ", "
						+ (contents != null ? "\tcontents=" + contents : "") + "]";	
			}
			return result;
		}
}