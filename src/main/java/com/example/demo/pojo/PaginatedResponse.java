package com.example.demo.pojo;

public class PaginatedResponse {

    int pageNumber;
    Object paginatedData;
    int perPageLimit;

    long totalCount;

    int offset;

    public PaginatedResponse(Object data, int pageNumber, int perPageLimit, long totalCount) {
        setPaginatedData(data);
        setPageNumber(pageNumber);
        setPerPageLimit(perPageLimit);
        setOffset((pageNumber-1)*perPageLimit);
        setTotalCount(totalCount);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Object getPaginatedData() {
        return paginatedData;
    }

    public void setPaginatedData(Object data) {
        this.paginatedData = data;
    }

    public int getPerPageLimit() {
        return perPageLimit;
    }

    public void setPerPageLimit(int perPageLimit) {
        this.perPageLimit = perPageLimit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
