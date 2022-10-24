package indiv.budin.entity.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudinFolderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BudinFolderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFolderIdIsNull() {
            addCriterion("folder_id is null");
            return (Criteria) this;
        }

        public Criteria andFolderIdIsNotNull() {
            addCriterion("folder_id is not null");
            return (Criteria) this;
        }

        public Criteria andFolderIdEqualTo(Integer value) {
            addCriterion("folder_id =", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotEqualTo(Integer value) {
            addCriterion("folder_id <>", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdGreaterThan(Integer value) {
            addCriterion("folder_id >", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("folder_id >=", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdLessThan(Integer value) {
            addCriterion("folder_id <", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdLessThanOrEqualTo(Integer value) {
            addCriterion("folder_id <=", value, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdIn(List<Integer> values) {
            addCriterion("folder_id in", values, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotIn(List<Integer> values) {
            addCriterion("folder_id not in", values, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdBetween(Integer value1, Integer value2) {
            addCriterion("folder_id between", value1, value2, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("folder_id not between", value1, value2, "folderId");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameIsNull() {
            addCriterion("folder_original_name is null");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameIsNotNull() {
            addCriterion("folder_original_name is not null");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameEqualTo(String value) {
            addCriterion("folder_original_name =", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameNotEqualTo(String value) {
            addCriterion("folder_original_name <>", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameGreaterThan(String value) {
            addCriterion("folder_original_name >", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameGreaterThanOrEqualTo(String value) {
            addCriterion("folder_original_name >=", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameLessThan(String value) {
            addCriterion("folder_original_name <", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameLessThanOrEqualTo(String value) {
            addCriterion("folder_original_name <=", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameLike(String value) {
            addCriterion("folder_original_name like", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameNotLike(String value) {
            addCriterion("folder_original_name not like", value, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameIn(List<String> values) {
            addCriterion("folder_original_name in", values, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameNotIn(List<String> values) {
            addCriterion("folder_original_name not in", values, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameBetween(String value1, String value2) {
            addCriterion("folder_original_name between", value1, value2, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderOriginalNameNotBetween(String value1, String value2) {
            addCriterion("folder_original_name not between", value1, value2, "folderOriginalName");
            return (Criteria) this;
        }

        public Criteria andFolderNameIsNull() {
            addCriterion("folder_name is null");
            return (Criteria) this;
        }

        public Criteria andFolderNameIsNotNull() {
            addCriterion("folder_name is not null");
            return (Criteria) this;
        }

        public Criteria andFolderNameEqualTo(String value) {
            addCriterion("folder_name =", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotEqualTo(String value) {
            addCriterion("folder_name <>", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameGreaterThan(String value) {
            addCriterion("folder_name >", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameGreaterThanOrEqualTo(String value) {
            addCriterion("folder_name >=", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLessThan(String value) {
            addCriterion("folder_name <", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLessThanOrEqualTo(String value) {
            addCriterion("folder_name <=", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameLike(String value) {
            addCriterion("folder_name like", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotLike(String value) {
            addCriterion("folder_name not like", value, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameIn(List<String> values) {
            addCriterion("folder_name in", values, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotIn(List<String> values) {
            addCriterion("folder_name not in", values, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameBetween(String value1, String value2) {
            addCriterion("folder_name between", value1, value2, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderNameNotBetween(String value1, String value2) {
            addCriterion("folder_name not between", value1, value2, "folderName");
            return (Criteria) this;
        }

        public Criteria andFolderPathIsNull() {
            addCriterion("folder_path is null");
            return (Criteria) this;
        }

        public Criteria andFolderPathIsNotNull() {
            addCriterion("folder_path is not null");
            return (Criteria) this;
        }

        public Criteria andFolderPathEqualTo(String value) {
            addCriterion("folder_path =", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathNotEqualTo(String value) {
            addCriterion("folder_path <>", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathGreaterThan(String value) {
            addCriterion("folder_path >", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathGreaterThanOrEqualTo(String value) {
            addCriterion("folder_path >=", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathLessThan(String value) {
            addCriterion("folder_path <", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathLessThanOrEqualTo(String value) {
            addCriterion("folder_path <=", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathLike(String value) {
            addCriterion("folder_path like", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathNotLike(String value) {
            addCriterion("folder_path not like", value, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathIn(List<String> values) {
            addCriterion("folder_path in", values, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathNotIn(List<String> values) {
            addCriterion("folder_path not in", values, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathBetween(String value1, String value2) {
            addCriterion("folder_path between", value1, value2, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderPathNotBetween(String value1, String value2) {
            addCriterion("folder_path not between", value1, value2, "folderPath");
            return (Criteria) this;
        }

        public Criteria andFolderUrlIsNull() {
            addCriterion("folder_url is null");
            return (Criteria) this;
        }

        public Criteria andFolderUrlIsNotNull() {
            addCriterion("folder_url is not null");
            return (Criteria) this;
        }

        public Criteria andFolderUrlEqualTo(String value) {
            addCriterion("folder_url =", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlNotEqualTo(String value) {
            addCriterion("folder_url <>", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlGreaterThan(String value) {
            addCriterion("folder_url >", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlGreaterThanOrEqualTo(String value) {
            addCriterion("folder_url >=", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlLessThan(String value) {
            addCriterion("folder_url <", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlLessThanOrEqualTo(String value) {
            addCriterion("folder_url <=", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlLike(String value) {
            addCriterion("folder_url like", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlNotLike(String value) {
            addCriterion("folder_url not like", value, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlIn(List<String> values) {
            addCriterion("folder_url in", values, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlNotIn(List<String> values) {
            addCriterion("folder_url not in", values, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlBetween(String value1, String value2) {
            addCriterion("folder_url between", value1, value2, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUrlNotBetween(String value1, String value2) {
            addCriterion("folder_url not between", value1, value2, "folderUrl");
            return (Criteria) this;
        }

        public Criteria andFolderUuidIsNull() {
            addCriterion("folder_uuid is null");
            return (Criteria) this;
        }

        public Criteria andFolderUuidIsNotNull() {
            addCriterion("folder_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andFolderUuidEqualTo(String value) {
            addCriterion("folder_uuid =", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidNotEqualTo(String value) {
            addCriterion("folder_uuid <>", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidGreaterThan(String value) {
            addCriterion("folder_uuid >", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidGreaterThanOrEqualTo(String value) {
            addCriterion("folder_uuid >=", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidLessThan(String value) {
            addCriterion("folder_uuid <", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidLessThanOrEqualTo(String value) {
            addCriterion("folder_uuid <=", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidLike(String value) {
            addCriterion("folder_uuid like", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidNotLike(String value) {
            addCriterion("folder_uuid not like", value, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidIn(List<String> values) {
            addCriterion("folder_uuid in", values, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidNotIn(List<String> values) {
            addCriterion("folder_uuid not in", values, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidBetween(String value1, String value2) {
            addCriterion("folder_uuid between", value1, value2, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderUuidNotBetween(String value1, String value2) {
            addCriterion("folder_uuid not between", value1, value2, "folderUuid");
            return (Criteria) this;
        }

        public Criteria andFolderSizeIsNull() {
            addCriterion("folder_size is null");
            return (Criteria) this;
        }

        public Criteria andFolderSizeIsNotNull() {
            addCriterion("folder_size is not null");
            return (Criteria) this;
        }

        public Criteria andFolderSizeEqualTo(Long value) {
            addCriterion("folder_size =", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeNotEqualTo(Long value) {
            addCriterion("folder_size <>", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeGreaterThan(Long value) {
            addCriterion("folder_size >", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("folder_size >=", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeLessThan(Long value) {
            addCriterion("folder_size <", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeLessThanOrEqualTo(Long value) {
            addCriterion("folder_size <=", value, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeIn(List<Long> values) {
            addCriterion("folder_size in", values, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeNotIn(List<Long> values) {
            addCriterion("folder_size not in", values, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeBetween(Long value1, Long value2) {
            addCriterion("folder_size between", value1, value2, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderSizeNotBetween(Long value1, Long value2) {
            addCriterion("folder_size not between", value1, value2, "folderSize");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeIsNull() {
            addCriterion("folder_build_time is null");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeIsNotNull() {
            addCriterion("folder_build_time is not null");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeEqualTo(Date value) {
            addCriterion("folder_build_time =", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeNotEqualTo(Date value) {
            addCriterion("folder_build_time <>", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeGreaterThan(Date value) {
            addCriterion("folder_build_time >", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("folder_build_time >=", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeLessThan(Date value) {
            addCriterion("folder_build_time <", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeLessThanOrEqualTo(Date value) {
            addCriterion("folder_build_time <=", value, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeIn(List<Date> values) {
            addCriterion("folder_build_time in", values, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeNotIn(List<Date> values) {
            addCriterion("folder_build_time not in", values, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeBetween(Date value1, Date value2) {
            addCriterion("folder_build_time between", value1, value2, "folderBuildTime");
            return (Criteria) this;
        }

        public Criteria andFolderBuildTimeNotBetween(Date value1, Date value2) {
            addCriterion("folder_build_time not between", value1, value2, "folderBuildTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}