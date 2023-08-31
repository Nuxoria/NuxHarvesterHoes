package me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types;

public enum FeatureType {

    XP_BOOSTER("item:xp-boost", "gui:xp-boost"),
    DOUBLE_DROPS("item:double-drops", "gui:double-drops"),
    AUTO_SELL("item:auto-sell", "gui:auto-sell"),
    TREASURE_HUNTER("item:treasure-hunter", "gui:treasure-hunter");

    private String itemTag;
    private String guiTag;
    FeatureType(String itemTag, String guiTag) {
        this.itemTag = itemTag;
        this.guiTag = guiTag;
    }

    public String getItemKey() {
        return itemTag;
    }

    public String getGuiKey() {
        return guiTag;
    }
}
