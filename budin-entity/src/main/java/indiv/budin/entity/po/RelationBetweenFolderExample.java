package indiv.budin.entity.po;

import java.util.ArrayList;
import java.util.List;

public class RelationBetweenFolderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RelationBetweenFolderExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdIsNull() {
            addCriterion("folder_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdIsNotNull() {
            addCriterion("folder_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdEqualTo(Integer value) {
            addCriterion("folder_parent_id =", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdNotEqualTo(Integer value) {
            addCriterion("folder_parent_id <>", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdGreaterThan(Integer value) {
            addCriterion("folder_parent_id >", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("folder_parent_id >=", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdLessThan(Integer value) {
            addCriterion("folder_parent_id <", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("folder_parent_id <=", value, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdIn(List<Integer> values) {
            addCriterion("folder_parent_id in", values, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdNotIn(List<Integer> values) {
            addCriterion("folder_parent_id not in", values, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdBetween(Integer value1, Integer value2) {
            addCriterion("folder_parent_id between", value1, value2, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("folder_parent_id not between", value1, value2, "folderParentId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdIsNull() {
            addCriterion("folder_child_id is null");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdIsNotNull() {
            addCriterion("folder_child_id is not null");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdEqualTo(Integer value) {
            addCriterion("folder_child_id =", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdNotEqualTo(Integer value) {
            addCriterion("folder_child_id <>", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdGreaterThan(Integer value) {
            addCriterion("folder_child_id >", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("folder_child_id >=", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdLessThan(Integer value) {
            addCriterion("folder_child_id <", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdLessThanOrEqualTo(Integer value) {
            addCriterion("folder_child_id <=", value, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdIn(List<Integer> values) {
            addCriterion("folder_child_id in", values, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdNotIn(List<Integer> values) {
            addCriterion("folder_child_id not in", values, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdBetween(Integer value1, Integer value2) {
            addCriterion("folder_child_id between", value1, value2, "folderChildId");
            return (Criteria) this;
        }

        public Criteria andFolderChildIdNotBetween(Integer value1, Integer value2) {
            addCriterion("folder_child_id not between", value1, value2, "folderChildId");
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