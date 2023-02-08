package com.utime.msword.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.utime.msword.objects.DocNumLevel;

/**
 * Document number level management class.
 */
public class DocNumLevelListMgr {
    /** paragraph ID */
    BigInteger numID;
    
    final List<DocNumLevel> subLevel;
    
    public DocNumLevelListMgr() {
        this.subLevel = new ArrayList<>();
    }
    
    public void clear() {
        this.subLevel.clear();
    }

	public void setNumID(BigInteger numID) {
		this.numID = numID;
	}

	public BigInteger getNumID(){
		return this.numID;
	}

    /**
     * 
     * @param format format type, decimal...
     * @param lvText format form "%1.", "%1.%2.", "%1.%2.%3.", ..
     * @param level This is the indentation level. The first is 0, the next is 1..
     * @return
     */
    public String getLevel(String format, String lvText, BigInteger level) {
        
        DocNumLevel selectLevel = null;
        
        final int lv = level.intValue();
        
        for( DocNumLevel numLevel : subLevel) {
            if( numLevel.getLevel() == lv ) {
                selectLevel = numLevel;
            }
        }
        
        if( selectLevel == null ) {
            selectLevel = new DocNumLevel();
            selectLevel.setLevel( lv );
            this.subLevel.add(selectLevel);
        }
        
        if( this.subLevel.size() > (lv+1) ) {
            for( int i=this.subLevel.size()-1 ; i>lv-1 ; i-- ) {
                this.subLevel.remove(i);
            }
        }
        
        selectLevel.increment();
        
        StringBuilder sb = new StringBuilder();
        final String [] sp = lvText.split("\\.");
        if( sp.length > 1 ) {
            for( DocNumLevel numLevel : subLevel) {
                sb.append( getLevelFormatNumber(format, lv, numLevel.getCount() ) ).append(".");
            }
        }else {
            sb.append( getLevelFormatNumber(format, lv, selectLevel.getCount() )  ).append("."); 
        }
        
        return sb.toString();
    }

	static final String[] ordinal = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };

	/** 
	 * Ordinal type 
	 * @param value
	 * @return
	 */
	static String getOrdinal(int value) {

		switch (value % 100) {
	    case 11: case 12: case 13:
	        return value + "th";
	    default:
	        return value + ordinal[value % 10];
	    }
	}
    
	static final String[] bullet = new String[] { "●", "■", "◆" };
	    
	/**
	 * Bullet 형식
	 * @param value
	 * @return
	 */
	static String getBullet(int value) {

		return bullet[value % 3];
	}

	static final RomanNumeralFormat romanFormat = new RomanNumeralFormat();

	static String getUpper(int value) {
		return romanFormat.format(value);
	}
	
	static String getLower(int i) {
		return getUpper(i).toLowerCase();
	}

    static String getLevelFormatNumber( String format, int level, int levelCount ) {
		String result;
		switch( format ) {
		case "decimal":{
			result = "" + levelCount;
			break;
		}
		case "ordinal":{
			result = getOrdinal(levelCount);
			break;
		}
		case "upperLetter":{
			result = getUpper(levelCount);
			break;
		}
		case "lowerRoman":{
			result = getLower(levelCount);
			break;
		}
		case "bullet":{
			result = getBullet(level);
			break;
		}
		default:{
			System.out.println("Unknown" + format);
			result = "";
		}
		}
		
		return result;
	}
}
