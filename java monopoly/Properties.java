import org.json.simple.JSONObject;

public class Properties extends Square {
    private Integer id;
    private Object name;
    private Integer cost;
    private Integer owned =0;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }
    public void setName(Object name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getOwned() {
        return owned;
    }
    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    public Properties(Integer id, Object name, Integer cost, Integer owned) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.owned = owned;
    }

    public Properties(){

    }
}
