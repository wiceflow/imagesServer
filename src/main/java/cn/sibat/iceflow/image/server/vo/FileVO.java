package cn.sibat.iceflow.image.server.vo;

/**
 * @author iceflow
 * @date 2018/8/6
 */
public class FileVO {
    private String name;
    private String path;

    public FileVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FileVO{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
