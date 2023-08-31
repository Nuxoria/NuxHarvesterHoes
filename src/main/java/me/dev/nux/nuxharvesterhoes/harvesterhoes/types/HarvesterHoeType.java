package me.dev.nux.nuxharvesterhoes.harvesterhoes.types;

public enum HarvesterHoeType {

    BASIC("basic-harvester-hoe"),
    PREMIUM("premium-harvester-hoe");

    private final String sectionName;

    HarvesterHoeType(String sectionName) {

        this.sectionName = sectionName;

    }

    public String getSectionName() {
        return sectionName;
    }

}
