package org.catacombae.hfsexplorer.types.hfs;

import java.io.PrintStream;
import org.catacombae.csjc.structelements.ASCIIStringField;
import org.catacombae.csjc.structelements.Dictionary;
import org.catacombae.hfsexplorer.Util;

/**
 * A file thread record in the HFS file system model.
 * 
 * This class was generated by CStructToJavaClass.
 */
public class CdrFThdRec extends CatDataRec {
    /*
     * struct CdrFThdRec
     * size: 46 bytes
     * description: 
     * 
     * BP  Size  Type       Identifier   Description
     * ---------------------------------------------------------------------
     * 0   1     SInt8      cdrType      record type (SignedByte)
     * 1   1     SInt8      cdrResrv2    reserved (SignedByte)
     * 2   4*2   SInt32[2]  fthdResrv    reserved (ARRAY[1..2] OF LongInt)
     * 10  4     SInt32     fthdParID    parent ID for this file (LongInt)
     * 14  1     UInt8      fthdCNameLen length of fthdCName (part of Str31)
     * 15  1*31  Char[31]   fthdCName    name of this file (part of Str31)
     */
    
    public static final int STRUCTSIZE = 46;
    
    private final byte[] fthdResrv = new byte[4*2];
    private final byte[] fthdParID = new byte[4];
    private final byte[] fthdCNameLen = new byte[1];
    private final byte[] fthdCName = new byte[1*31];

    /**
     * Creates a new HFS file thread record by reading from the given data at
     * the specified offset.
     * 
     * @param data
     * @param offset
     */
    public CdrFThdRec(byte[] data, int offset) {
        super(data, offset);
        System.arraycopy(data, offset + 2, fthdResrv, 0, 4 * 2);
        System.arraycopy(data, offset + 10, fthdParID, 0, 4);
        System.arraycopy(data, offset + 14, fthdCNameLen, 0, 1);
        System.arraycopy(data, offset + 15, fthdCName, 0, 1 * 31);
    }
    
    public static int length() { return STRUCTSIZE; }
    
    /** reserved (ARRAY[1..2] OF LongInt) */
    public int[] getFthdResrv() { return Util.readIntArrayBE(fthdResrv); }
    /** parent ID for this file (LongInt) */
    public int getFthdParID() { return Util.readIntBE(fthdParID); }
    /** length of fthdCName (part of Str31) */
    public byte getFthdCNameLen() { return Util.readByteBE(fthdCNameLen); }
    /** name of this file (part of Str31) */
    public byte[] getFthdCName() { return Util.readByteArrayBE(fthdCName); }
    
    @Override
    public void printFields(PrintStream ps, String prefix) {
        super.printFields(ps, prefix);
        ps.println(prefix + " fthdResrv: " + getFthdResrv());
        ps.println(prefix + " fthdParID: " + Util.unsign(getFthdParID()));
        ps.println(prefix + " fthdCNameLen: " + Util.unsign(getFthdCNameLen()));
        ps.println(prefix + " fthdCName: \"" + Util.toASCIIString(getFthdCName()) + "\"");
    }
    
    @Override
    public void print(PrintStream ps, String prefix) {
        ps.println(prefix + "CdrFThdRec:");
        printFields(ps, prefix);
    }
    
    @Override
    public byte[] getBytes() {
        byte[] result = new byte[STRUCTSIZE];
        int offset = 0;

        byte[] superData = super.getBytes();
        System.arraycopy(superData, 0, result, offset, superData.length); offset += superData.length;
        System.arraycopy(fthdResrv, 0, result, offset, fthdResrv.length); offset += fthdResrv.length;
        System.arraycopy(fthdParID, 0, result, offset, fthdParID.length); offset += fthdParID.length;
        System.arraycopy(fthdCNameLen, 0, result, offset, fthdCNameLen.length); offset += fthdCNameLen.length;
        System.arraycopy(fthdCName, 0, result, offset, fthdCName.length); offset += fthdCName.length;
        return result;
    }

    @Override
    public int size() {
        return length();
    }

    @Override
    public Dictionary getStructElements() {
        DictionaryBuilder db = new DictionaryBuilder(CdrFThdRec.class.getSimpleName());
        
        super.addSuperStructElements(db);
        db.addIntArray("fthdResrv", fthdResrv, BITS_32, UNSIGNED, BIG_ENDIAN, "Reserved", HEXADECIMAL);
        db.addUIntBE("fthdParID", fthdParID, "Parent ID");
        db.addUIntBE("fthdCNameLen", fthdCNameLen, "Length of record name", "bytes");
        db.add("fthdCName", new ASCIIStringField(fthdCName), "Record name");
        
        return db.getResult();
    }
}
