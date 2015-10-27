package ragmb.domotechapp.objects;

/**
 * Created by VíctorMoisés on 25/10/2015.
 */
public class Device {
    private String id;
    private String status;

    public Device(String id, String status) {
        this.id = id;
        this.status = status;
    }
    public Device(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
