public class Exercise {
  /**
   * The name of this exercise.
   */
  private String name;

  /**
   * Denotes whether this exercise is assisted; e.g: uses negative weights.
   */
  private boolean isAssisted;

  /**
   * Default constructor for the exercise object.
   */
  public Exercise(){
    name = "N/A";
    isAssisted = false;
  }

  /**
   * Creates a new Exercise object of the given name.
   * @param name the name to give this Exercise object.
   */
  public Exercise(String name) {
    this.name = name;
    isAssisted = false;
  }

  /**
   * Creates a new Exercise object of the given name.
   * @param name the name to give this Exercise object.
   * @param isAssisted whether this Exercise can be assisted with negative weights.
   */
  public Exercise(String name, boolean isAssisted) {
    this.name = name;
    this.isAssisted = isAssisted;
  }

  public boolean isAssisted(){
    return isAssisted;
  }

  public String getName(){
    return name;
  }

  public boolean equals(Exercise e){
    return this.isAssisted == e.isAssisted() && this.name.equals(e.getName());
  }

  /**
   * Sets whether this exercise is assisted.
   * @param b the boolean value of whether this exercise is assisted.
   */
  public void setAssisted(boolean b){
    isAssisted = b;
  }

  public String toString(){
    return name;
  }
}
