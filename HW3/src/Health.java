public class Health {
    private Integer healthPool;
    private Integer healthAmount;


    public Health(Integer healthPool, Integer healthAmount) {
        this.healthPool = healthPool;
        this.healthAmount = healthAmount;
    }

    public Integer getHealthPool() {
        return healthPool;
    }

    public Integer getHealthAmount() {
        return healthAmount;
    }

    public void setHealthPool(Integer healthPool) {
        this.healthPool = healthPool;
    }

    public void setHealthAmount(Integer healthAmount) {
        this.healthAmount = healthAmount;
    }
}
