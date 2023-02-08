package com.utime.msword.objects;

import java.util.ArrayList;
import java.util.List;

import com.utime.msword.define.EDocType;

import lombok.Data;

/**
 * Document table object.
 */
@Data
public class DocTable  extends AbsDocObject{
		
    private static final long serialVersionUID = 8502127629699783001L;

    /**
     * width size
     */
    int width;
    
    /**
     * Table contents can have different objects, so they include DocElement.
     */
    final List<List<DocElement>> rows = new ArrayList<>();
    
    /**
     * Cell's horizontal size.
     */
    final List<Integer> cellWidthList = new ArrayList<>();
    
    public DocTable() {
        super(EDocType.Table);
    }
    
    @Override
    public String toString() {
        
        final int heightCount = rows.size();
        final int widthCount = cellWidthList.size();

        return "DocTable [heightCount=" + heightCount + ", widthCount=" + widthCount + ", "
        + (rows != null ? "\n" + rows : "") + "]";
    }
    
}
