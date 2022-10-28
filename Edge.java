public class Edge {
    
  public int u;
  public int v;
  public int w;

  public Edge(int u, int v, int w) {
    this.u = u;
    this.v = v;
    this.w = w;
  }

  public int getSource() {
    return u;
  }

  public void setSource(int source) {
    this.u = source;
  }

  public int getSink() {
    return v;
  }

  public void setSink(int sink) {
    this.v = sink;
  }

  public int getWeight() {
    return w;
  }

  public void setWeight(int weight) {
    this.w = weight;
  }

 
  public String toString() {
    String str = "("+ this.u + ","+ this.v + ","+ this.w + ")";
    return str;
}
}