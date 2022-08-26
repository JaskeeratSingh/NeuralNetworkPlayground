package persistence;

import org.json.JSONObject;

public interface Writeable {
    JSONObject toJson(String name);
}
