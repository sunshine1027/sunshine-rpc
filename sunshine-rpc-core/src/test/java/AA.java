/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class AA {
    private int id;
    private BB bb;

    public AA() {
    }

    public AA(int id, BB bb) {
        this.id = id;
        this.bb = bb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BB getBb() {
        return bb;
    }

    public void setBb(BB bb) {
        this.bb = bb;
    }

    @Override
    public String toString() {
        return "AA{" +
                "id=" + id +
                ", bb=" + bb +
                '}';
    }
}
