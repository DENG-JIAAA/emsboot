package top.dj.enumeration;

public enum ApplyStatus {

    PENDING("待审核", 1),
    APPROVAL("审核中", 2),
    PASS("通过", 3),
    REJECT("驳回", 4),
    USING("使用中", 5),
    RETURNED("已返还", 6),
    STORED("已入库", 7),
    MAINTENANCE("维修中", 8),
    SCRAPPED("已报废", 9);

    private String name;
    private Integer index;

    ApplyStatus(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
