import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ExerciseInput {
  private JFrame exerciseInput;

  private JFrame msgLabel;

  // private final String[] choices = {"Bicep curl", "Tricep extension"};

  private Exercise[] choices;

  /**
   * Loads a list of assisted exercises.
   * @return a hash set containing the assisted exercises.
   */
  private ArrayList<Exercise> loadAssistedExercises(){
    ArrayList<Exercise> assistedExerciseSet = new ArrayList<>();

    try {
      Scanner s = new Scanner(new File("./ExerciseList/AssistedExerciseList.txt"));

      while(s.hasNextLine()){
        Exercise currExercise = new Exercise(s.nextLine(), true);
        assistedExerciseSet.add(currExercise);
      }

      return assistedExerciseSet;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private Exercise[] loadExercises() {
    // Read from the file
    // Write elements of the file to the choices array

    ArrayList<Exercise> tmpList = new ArrayList<>();
    ArrayList<Exercise> assistedExerciseList = loadAssistedExercises();

    try {
      Scanner s = new Scanner(new File("./ExerciseList/ExerciseList.txt"));

      while(s.hasNextLine()){
        String currLine = s.nextLine();
        Exercise currExercise = new Exercise(currLine);

        if(assistedExerciseList.contains(currExercise)){
          currExercise.setAssisted(true);
        }


        tmpList.add(currExercise);
      }

      return tmpList.toArray(new Exercise[0]);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void appendWeightLabel(){
    JLabel weightLabel = new JLabel("What is your bodyweight?");
    exerciseInput.add(weightLabel);

    SpinnerModel bodyWeight = new SpinnerNumberModel(0, 0, 300, 1);
    JSpinner weightSpinner = new JSpinner(bodyWeight);
    exerciseInput.add(weightSpinner);
  }

  /**
   * Prompts the user to input an exercise name.
   * @return the JComboBox of exercise choices.
   */
  private JComboBox<Exercise> promptExercise(){
    JLabel exerciseTypeLabel = new JLabel("What exercise are you doing?");
    exerciseInput.add(exerciseTypeLabel);

    choices = loadExercises();
    final JComboBox<Exercise> exerciseTypeBox = new JComboBox<>(choices);
    exerciseTypeBox.setVisible(true);
    exerciseInput.add(exerciseTypeBox);

    return exerciseTypeBox;
  }

  /**
   * Prompts the user to input the number of reps they did an exercise.
   */
  private void promptReps(){
    JLabel repCountLabel = new JLabel("How many reps?");
    exerciseInput.add(repCountLabel);

    SpinnerModel repCount = new SpinnerNumberModel(0, 0, 20, 1);
    JSpinner repSpinner = new JSpinner(repCount);
    exerciseInput.add(repSpinner);
  }

  /**
   * Prompts the user to input the number of sets they did an exercise.
   */
  private void promptSets(){
    JLabel setCountLabel = new JLabel("How many sets?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(1, 1, 10, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);
  }

  /**
   * Prompts the user to input the weight they did an exercise.
   */
  private void promptWeights(){
    JLabel setCountLabel = new JLabel("How heavy were the weights?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(2.5, 2.5, 350, 2.5);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);
  }

  /**
   * Prompts the user to input their bodyweight.
   */
  private void promptBodyWeight(){
    JLabel setCountLabel = new JLabel("What is your bodyweight [lbs]?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(120, 2.5, 350, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);
  }

  private void initializeWindow(){
    exerciseInput = new JFrame();
    exerciseInput.setSize(400, 400);
    exerciseInput.setLayout(new FlowLayout());
    exerciseInput.setTitle("Add a new exercise");
  }

  /**
   * Adds a prompt to allow the user to add a weight label if the given exercise is assisted.
   * @param currChoice the current exercise choice.
   */
  private void appendWeightLabelIfNecessary(Exercise currChoice){
    assert currChoice != null;
    if(currChoice.isAssisted()){
      appendWeightLabel();
    }
  }

  private void promptIfAssistedExercise(){
    JLabel setCountLabel = new JLabel("Is this exercise assisted?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(0, 0, 1, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);
  }

  private void displayTrueWeightsLifted(int weightLifted, int bodyWeight, int isAssisted){
    int trueWeight = (isAssisted == 1) ? weightLifted - bodyWeight : weightLifted;
    JLabel trueWeightLifted = new JLabel("The true amount of weight lifted is " + trueWeight);
    exerciseInput.add(trueWeightLifted);
  }

  public ExerciseInput(){
    initializeWindow();

    promptExercise();
    promptReps();
    promptSets();
    promptWeights();
    promptIfAssistedExercise();
    promptBodyWeight();

    // displayTrueWeightsLifted(weightLifted, bodyWeight, isAssisted);
  }

  private void demo() {

    exerciseInput.setVisible(true);
    exerciseInput.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        exerciseInput.dispose();
      }
    });
  }

  public static void main(String[] args){
    ExerciseInput input = new ExerciseInput();
    input.demo();
  }
}
