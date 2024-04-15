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

  private final Notifications notif = new Notifications();
  private final StreakCounter streak = new StreakCounter();
  private final JLabel streakMsg = new JLabel("Streak: " + streak.getStreak() + "	Streak Freezes: " + streak.getStreakFreezes(), JLabel.CENTER);

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

  /**
   * Prompts the user to input an exercise name.
   * @return the JComboBox of exercise choices.
   */
  private JComboBox<Exercise> promptExercise(){
    JLabel exerciseTypeLabel = new JLabel("What exercise are you doing?");
    exerciseInput.add(exerciseTypeLabel);

    Exercise[] choices = loadExercises();
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

  private JComboBox<Boolean> promptIfAssistedExercise(){
    JLabel setCountLabel = new JLabel("Is this exercise assisted?");
    exerciseInput.add(setCountLabel);

    JComboBox<Boolean> isAssistedBox = new JComboBox<>(new Boolean[]{true, false});
    isAssistedBox.setVisible(true);

    exerciseInput.add(isAssistedBox);

    return isAssistedBox;
  }

  private JButton promptSave(){
    JButton saveButton = new JButton("SAVE");
    exerciseInput.add(saveButton);

    return saveButton;
  }

  private JButton promptNotif(int on){
	  JButton notifButton;
	  if(on == 1) {
		  notifButton = new JButton("Notifications ON");
	  }
	  else {
    	notifButton = new JButton("Notifications OFF");
	  }
	  exerciseInput.add(notifButton);
	  return notifButton;
  }

  public ExerciseInput() {
    initializeWindow();

    File exerciseLog = new File("./exerciseLog.txt");

    JComboBox<Exercise> exerciseJComboBox = promptExercise();
    JSpinner repSpinner = promptReps();
    JSpinner setSpinner = promptSets();
    JSpinner weightSpinner = promptWeights();

    JComboBox<Boolean> isAssistedBox = promptIfAssistedExercise();
    JSpinner bodyWeightSpinner = promptBodyWeight();

    JButton saveButton = promptSave();

    Exercise exercise = (Exercise) exerciseJComboBox.getSelectedItem();

    JButton notifOnButton = promptNotif(1);
    JButton notifOffButton = promptNotif(0);

    int countReps = (int) repSpinner.getValue();
    int countSets = (int) setSpinner.getValue();
    double weightAmount = (double) weightSpinner.getValue();

    assert isAssistedBox.getSelectedItem() != null;
    boolean isAssisted = (boolean) isAssistedBox.getSelectedItem();

    double bodyWeight = (double) bodyWeightSpinner.getValue();

    saveButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          writeExerciseToLog(
              exerciseLog, exercise, countReps, countSets, weightAmount, isAssisted, bodyWeight);

          displaySaveSuccessful();

          streak.streakUpdate();
          streakMsg.setText("Streak: " + streak.getStreak() + "	Streak Freezes: " + streak.getStreakFreezes());

        } catch (FileNotFoundException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    notifOnButton.addActionListener(e -> {
      System.out.println("This line has been triggered");
      notif.notifOn();
      notif.notif();
    });

    notifOffButton.addActionListener(e -> {
      System.out.println("This line has been triggered");
      notif.notifOff();
    });

    exerciseInput.add(streakMsg);
  }

  private void displaySaveSuccessful(){
    JFrame saveFrame = new JFrame();
    saveFrame.setSize(400, 100);
    saveFrame.setLayout(new FlowLayout());

    JPanel saveMessage = new JPanel();
    saveMessage.add(new JLabel("Save successful!"));

    saveFrame.add(saveMessage);

    saveFrame.setVisible(true);
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

  public static void main(String[] args) {
    ExerciseInput input = new ExerciseInput();
    input.demo();
  }
}
