import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ExerciseInput {
  private JFrame exerciseInput;

  private Exercise[] choices;

  /**
   * Loads a list of assisted exercises.
   * @return a hash set containing the assisted exercises.
   */
  private ArrayList<Exercise> loadAssistedExercises(){
    ArrayList<Exercise> assistedExerciseSet = new ArrayList<>();

    try {
      Scanner s = new Scanner(new File("./exercises.txt"));

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
      Scanner s = new Scanner(new File("./exercises.txt"));

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
  private JSpinner promptReps(){
    JLabel repCountLabel = new JLabel("How many reps?");
    exerciseInput.add(repCountLabel);

    SpinnerModel repCount = new SpinnerNumberModel(0, 0, 20, 1);
    JSpinner repSpinner = new JSpinner(repCount);
    exerciseInput.add(repSpinner);

    return repSpinner;
  }

  /**
   * Prompts the user to input the number of sets they did an exercise.
   */
  private JSpinner promptSets(){
    JLabel setCountLabel = new JLabel("How many sets?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(1, 1, 10, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);

    return setSpinner;
  }

  /**
   * Prompts the user to input the weight they did an exercise.
   */
  private JSpinner promptWeights(){
    JLabel weightCountLabel = new JLabel("How heavy were the weights?");
    exerciseInput.add(weightCountLabel);

    SpinnerModel weightSpinnerModel = new SpinnerNumberModel(2.5, 2.5, 350, 2.5);
    JSpinner weightSpinner = new JSpinner(weightSpinnerModel);
    exerciseInput.add(weightSpinner);

    return weightSpinner;
  }

  /**
   * Prompts the user to input their bodyweight.
   */
  private JSpinner promptBodyWeight(){
    JLabel setCountLabel = new JLabel("What is your bodyweight [lbs]?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(120, 2.5, 350, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);

    return setSpinner;
  }

  private void initializeWindow(){
    exerciseInput = new JFrame();
    exerciseInput.setSize(400, 400);
    exerciseInput.setLayout(new FlowLayout());
    exerciseInput.setTitle("Add a new exercise");
  }

  private JSpinner promptIfAssistedExercise(){
    JLabel setCountLabel = new JLabel("Is this exercise assisted?");
    exerciseInput.add(setCountLabel);

    SpinnerModel setCount = new SpinnerNumberModel(0, 0, 1, 1);
    JSpinner setSpinner = new JSpinner(setCount);
    exerciseInput.add(setSpinner);

    return setSpinner;
  }

  private JButton promptSave(){
    JButton saveButton = new JButton("SAVE");
    exerciseInput.add(saveButton);

    return saveButton;
  }

  public ExerciseInput() throws AWTException {
    initializeWindow();

    File exerciseLog = new File("./exerciseLog.txt");

    JComboBox<Exercise> exerciseJComboBox = promptExercise();
    JSpinner repSpinner = promptReps();
    JSpinner setSpinner = promptSets();
    JSpinner weightSpinner = promptWeights();

    JSpinner assistedSpinner = promptIfAssistedExercise();
    JSpinner bodyWeightSpinner = promptBodyWeight();

    JButton saveButton = promptSave();

    Exercise exercise = (Exercise) exerciseJComboBox.getSelectedItem();
    Notifications.main(new String[]{""});

    int countReps = (int) repSpinner.getValue();
    int countSets = (int) setSpinner.getValue();
    double weightAmount = (double) weightSpinner.getValue();
    boolean isAssisted = (int) assistedSpinner.getValue() == 1;
    double bodyWeight = (double) bodyWeightSpinner.getValue();

    saveButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("This line has been triggered");
        try {
          writeExerciseToLog(
              exerciseLog, exercise, countReps, countSets, weightAmount, isAssisted, bodyWeight);

          displaySaveSuccessful(saveButton);
        } catch (FileNotFoundException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    // displayTrueWeightsLifted(weightLifted, bodyWeight, isAssisted);
  }

  private void displaySaveSuccessful(JButton saveButton){


  }

  /**
   * Writes the given exercise into the exercise log.
   *
   * @param exerciseLog the file for the exercise log.
   * @param exercise the data for the Exercise.
   * @param countReps the number of reps performed.
   * @param countSets the number of sets performed.
   * @param weightAmount the nominal weight lifted.
   * @param isAssisted whether the exercise used negative weights.
   * @param bodyWeight the user's bodyweight.
   * @throws FileNotFoundException if the exercise log file is null.
   */
  private void writeExerciseToLog(
      File exerciseLog, Exercise exercise, int countReps,
      int countSets, double weightAmount, boolean isAssisted,
      double bodyWeight) throws FileNotFoundException {

    try {
      PrintWriter writer = new PrintWriter(exerciseLog);
      double weightLifted = (isAssisted ? bodyWeight - weightAmount : weightAmount);

      writer.println(exercise + "," + countReps + "," + countSets + "," + weightLifted);
      writer.close();
    } catch (FileNotFoundException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private void demo() {

    exerciseInput.setVisible(true);
    exerciseInput.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        exerciseInput.dispose();
      }
    });
  }

  public static void main(String[] args) throws AWTException {
    ExerciseInput input = new ExerciseInput();
    input.demo();
  }
}
