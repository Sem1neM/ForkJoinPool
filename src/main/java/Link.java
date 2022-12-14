import java.util.concurrent.CopyOnWriteArraySet;

public class Link {
    private String url;
    private volatile Link parent;
    private volatile int depth;
    private volatile CopyOnWriteArraySet<Link> children;

    public Link(String url) {
        this.url = url;
        children = new CopyOnWriteArraySet<>();
        depth = 0;
        parent = null;

    }

    private void setParent(Link link) {
        synchronized (this) {
            this.parent = link;
            this.depth = setDepth();
        }
    }

    public int getDepth() {
        return depth;
    }

    private int setDepth() {
        if (parent == null) {
            return 0;
        }
        return 1 + parent.getDepth();
    }


    public CopyOnWriteArraySet<Link> getChildren() {
        return children;
    }

    public String getUrl() {
        return url;
    }

    public void addChildren(Link link) {
        if (!children.contains(link) && link.getUrl().startsWith(url)) {
            this.children.add(link);
            link.setParent(this);
        }
    }
}