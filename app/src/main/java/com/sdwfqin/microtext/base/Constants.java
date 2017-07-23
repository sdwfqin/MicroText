package com.sdwfqin.microtext.base;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;

public class Constants {

    //================= 缓存 ====================
    /**
     * 路径：/data/data/com.sdwfqin.microtext/cache/data
     */
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    /**
     * 路径：/data/data/com.sdwfqin.microtext/cache/data/NetCache
     */
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    /**
     * 图片保存的确切位置
     */
    public static final String SAVE_REAL_PATH = SDCardUtils.getSDCardPath() + "MicrotText";

    //================= splash ====================
    public static final long DELAY = 3000;
    public static final int CODE_ENTER_HOME = 1;

    //================= Essay ====================
    public static final int CODE_MINGJIA = 0;
    public static final int CODE_JDMW = 1;
    public static final int CODE_LOVE = 2;
    public static final int CODE_SHILIAN = 3;
    public static final int CODE_XINQING = 4;
    public static final int CODE_ZHELI = 5;
    public static final int CODE_STORY = 6;

    public static final String[] ESSAY_URL = new String[]{"mingjia/?p=", "jdmw/?p=",
            "love/?p=", "shilian/?p=", "xinqing/?p=",
            "zheli/?p=", "story/?p="};

    //================= JuZiMi ====================
    public static final int CODE_MEITU = 0;
    public static final int CODE_SHOUXIE = 1;
    public static final int CODE_DUIBAI = 2;

    public static final String[] JUZIMI_URL = new String[]{"", "shouxiemeiju",
            "jingdianduibai"};

    public static final String[] JUZIMI_TEXT = new String[]{"美图美句", "手写美句",
            "经典对白"};
}
