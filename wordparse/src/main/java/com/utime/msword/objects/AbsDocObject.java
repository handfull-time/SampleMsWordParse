package com.utime.msword.objects;

import java.io.Serializable;

import com.utime.msword.define.EDocType;

/**
 * Superclass of the MS word doc object.
 */
public abstract class AbsDocObject implements DocObject, Serializable {

    private static final long serialVersionUID = 5334715584962937770L;

    /**
     * document type
     */
    final EDocType docType;
		
    public AbsDocObject(EDocType docType) {
        this.docType = docType;
    }
    
    @Override
    public EDocType getDocType() {
        return this.docType;
    }
}
