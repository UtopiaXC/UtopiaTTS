package com.utopiaxc.utopiatts.tts.enums;

import androidx.annotation.NonNull;

public enum Actors {
    ZH_CN_XIAOCHEN_NEURAL("ZH_CN_XIAOCHEN_NEURAL", "zh-CN", "zh-CN-XiaochenNeural", 'F',false),
    ZH_CN_XIAOHAN_NEURAL("ZH_CN_XIAOHAN_NEURAL", "zh-CN", "zh-CN-XiaohanNeural", 'F',false),
    ZH_CN_XIAOMENG_NEURAL("ZH_CN_XIAOMENG_NEURAL", "zh-CN", "zh-CN-XiaomengNeural", 'F',true),
    ZH_CN_XIAOMO_NEURAL("ZH_CN_XIAOMO_NEURAL", "zh-CN", "zh-CN-XiaomoNeural", 'F',false),
    ZH_CN_XIAOQIU_NEURAL("ZH_CN_XIAOQIU_NEURAL", "zh-CN", "zh-CN-XiaoqiuNeural", 'F',false),
    ZH_CN_XIAORUI_NEURAL("ZH_CN_XIAORUI_NEURAL", "zh-CN", "zh-CN-XiaoruiNeural", 'F',false),
    ZH_CN_XIAOSHUANG_NEURAL("ZH_CN_XIAOSHUANG_NEURAL", "zh-CN", "zh-CN-XiaoshuangNeural", 'F',false),
    ZH_CN_XIAOXIAO_NEURAL("ZH_CN_XIAOXIAO_NEURAL", "zh-CN", "zh-CN-XiaoxiaoNeural", 'F',false),
    ZH_CN_XIAOXUAN_NEURAL("ZH_CN_XIAOXUAN_NEURAL", "zh-CN", "zh-CN-XiaoxuanNeural", 'F',false),
    ZH_CN_XIAOYAN_NEURAL("ZH_CN_XIAOYAN_NEURAL", "zh-CN", "zh-CN-XiaoyanNeural", 'F',false),
    ZH_CN_XIAOYI_NEURAL("ZH_CN_XIAOYI_NEURAL", "zh-CN", "zh-CN-XiaoyiNeural", 'F',true),
    ZH_CN_XIAOYOU_NEURAL("ZH_CN_XIAOYOU_NEURAL", "zh-CN", "zh-CN-XiaoyouNeural", 'F',false),
    ZH_CN_XIAOZHEN_NEURAL("ZH_CN_XIAOZHEN_NEURAL", "zh-CN", "zh-CN-XiaozhenNeural", 'F',true),
    ZH_CN_YUNFENG_NEURAL("ZH_CN_YUNFENG_NEURAL", "zh-CN", "zh-CN-YunfengNeural", 'M',true),
    ZH_CN_YUNHAO_NEURAL("ZH_CN_YUNHAO_NEURAL", "zh-CN", "zh-CN-YunhaoNeural", 'M',true),
    ZH_CN_YUNJIAN_NEURAL("ZH_CN_YUNJIAN_NEURAL", "zh-CN", "zh-CN-YunjianNeural", 'M',true),
    ZH_CN_YUNXIA_NEURAL("ZH_CN_YUNXIA_NEURAL", "zh-CN", "zh-CN-YunxiaNeural", 'M',true),
    ZH_CN_YUNXI_NEURAL("ZH_CN_YUNXI_NEURAL", "zh-CN", "zh-CN-YunxiNeural", 'M',false),
    ZH_CN_YUNYANG_NEURAL("ZH_CN_YUNYANG_NEURAL", "zh-CN", "zh-CN-YunyangNeural", 'M',false),
    ZH_CN_YUNYE_NEURAL("ZH_CN_YUNYE_NEURAL", "zh-CN", "zh-CN-YunyeNeural", 'M',false),
    ZH_CN_YUNZE_NEURAL("ZH_CN_YUNZE_NEURAL", "zh-CN", "zh-CN-YunzeNeural", 'M',true),
    ZH_CN_HENAN_YUNDENG_NEURAL("ZH_CN_HENAN_YUNDENG_NEURAL", "zh-CN-henan", "zh-CN-henan-YundengNeural", 'M',true),
    ZH_CN_LIAONING_XIAOBEI_NEURAL("ZH_CN_LIAONING_XIAOBEI_NEURAL", "zh-CN-liaoning", "zh-CN-liaoning-XiaobeiNeural", 'F',true),
    ZH_CN_SHAANXI_XIAONI_NEURAL("ZH_CN_SHAANXI_XIAONI_NEURAL", "zh-CN-shaanxi", "zh-CN-shaanxi-XiaoniNeural", 'F',true),
    ZH_CN_SHANDONG_YUNXIANG_NEURAL("ZH_CN_SHANDONG_YUNXIANG_NEURAL", "zh-CN-shandong", "zh-CN-shandong-YunxiangNeural", 'M',true),
    ZH_CN_SICHUAN_YUNXI_NEURAL("ZH_CN_SICHUAN_YUNXI_NEURAL", "zh-CN-sichuan", "zh-CN-sichuan-YunxiNeural", 'M',true),
    ZH_HK_HIUGAAI_NEURAL("ZH_HK_HIUGAAI_NEURAL", "zh-HK", "zh-HK-HiuGaaiNeural", 'F',false),
    ZH_HK_HIUMAAN_NEURAL("ZH_HK_HIUMAAN_NEURAL", "zh-HK", "zh-HK-HiuMaanNeural", 'F',false),
    ZH_HK_WANLUNG_NEURAL("ZH_HK_WANLUNG_NEURAL", "zh-HK", "zh-HK-WanLungNeural", 'M',false),
    ZH_TW_HSIAOCHEN_NEURAL("ZH_TW_HSIAOCHEN_NEURAL", "zh-TW", "zh-TW-HsiaoChenNeural", 'F',false),
    ZH_TW_HSIAOYU_NEURAL("ZH_TW_HSIAOYU_NEURAL", "zh-TW", "zh-TW-HsiaoYuNeural", 'F',false),
    ZH_TW_YUNJHE_NEURAL("ZH_TW_YUNJHE_NEURAL", "zh-TW", "zh-TW-YunJheNeural", 'M',false);

    private final String mName;
    private final String mRegion;
    private final String mId;
    private final Character mGender;
    private final boolean mIsPre;

    Actors(String name, String region, String id, Character gender, boolean isPre) {
        mName = name;
        mRegion = region;
        mId = id;
        mGender = gender;
        mIsPre = isPre;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }


    public static Actors getActor(String name) {
        for (Actors actors : Actors.values()) {
            if (actors.getName().equals(name)) {
                return actors;
            }
        }
        return ZH_CN_XIAOXIAO_NEURAL;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getRegion() {
        return mRegion;
    }

    public Character getGender() {
        return mGender;
    }

    public boolean isFemale() {
        return mGender == 'F';
    }

    public boolean isPre(){
        return mIsPre;
    }
}
