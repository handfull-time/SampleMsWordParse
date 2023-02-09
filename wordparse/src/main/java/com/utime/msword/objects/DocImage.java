package com.utime.msword.objects;

import com.utime.msword.define.EDocPictureType;
import com.utime.msword.define.EDocType;

import lombok.Data;

/**
 * Document image object
 */
@Data
public class DocImage  extends AbsDocObject{
		
    private static final long serialVersionUID = -7707742690322185800L;
    
    /** name */
    String name;
    /** width size */
    double width;
    /** data base64 */
    String data;
    /** picture type */
    EDocPictureType pictureType;
    
    public DocImage() {
        super(EDocType.Image);
    }
    
    @Override
    public String toString() {
        
        final String logDt;
        if( data == null ) {
            logDt = null;
        }else {
            if( data.length() > 10 ) {
                logDt = data.substring(0,  10);
            }else {
                logDt = data;
            }
        }
        
        return "DocImage [" + (name != null ? "name=" + name + ", " : "") + "width=" + width + ", type=" + pictureType
                + (data != null ? "data=" + logDt : "") + "]";
    }
    
}
