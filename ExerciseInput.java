import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    JButton historyButton = promptHistory();

    Exercise exercise = (Exercise) exerciseJComboBox.getSelectedItem();

    JButton notifOnButton = promptNotif(1);
    JButton notifOffButton = promptNotif(0);

    int countReps = (int) repSpinner.getValue();
    int countSets = (int) setSpinner.getValue();
    double weightAmount = (double) weightSpinner.getValue();

    assert isAssistedBox.getSelectedItem() != null;
    boolean isAssisted = (Boolean) isAssistedBox.getSelectedItem();

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

    historyButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          displayHistory(exerciseLog);
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

  private void displayHistory(File exerciseLog) throws FileNotFoundException{
    try{
      JFrame historyFrame = new JFrame();
      historyFrame.setLayout(new GridLayout(1,4,100,0));
      historyFrame.setSize(500, 400);
      historyFrame.setLayout(new FlowLayout());
      historyFrame.setTitle("Exercise History");
      historyFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent windowEvent) {
          historyFrame.dispose();
        }
      });
      JPanel exercisePanel = new JPanel();
      exercisePanel.setLayout(new BoxLayout(exercisePanel, BoxLayout.Y_AXIS));
      JPanel repsPanel = new JPanel();
      repsPanel.setLayout(new BoxLayout(repsPanel, BoxLayout.Y_AXIS));
      JPanel setsPanel = new JPanel();
      setsPanel.setLayout(new BoxLayout(setsPanel, BoxLayout.Y_AXIS));
      JPanel weightPanel = new JPanel();
      weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));
      historyFrame.add(exercisePanel);
      historyFrame.add(repsPanel);
      historyFrame.add(setsPanel);
      historyFrame.add(weightPanel);

      Scanner reader = new Scanner(exerciseLog);
      String[] line;
      JLabel header1 = new JLabel("Exercise");
      header1.setBorder(new EmptyBorder(0, 0, 0, 100));
      JLabel header2 = new JLabel("Reps");
      header2.setBorder(new EmptyBorder(0, 0, 0, 40));
      JLabel header3 = new JLabel("Sets\t");
      header3.setBorder(new EmptyBorder(0, 0, 0, 40));
      JLabel header4 = new JLabel("Weight Lifted(lbs)");
      header4.setBorder(new EmptyBorder(0, 0, 0, 40));
      exercisePanel.add(header1);
      repsPanel.add(header2);
      setsPanel.add(header3);
      weightPanel.add(header4);
      while(reader.hasNextLine()){
        line = reader.nextLine().split(",", 0);
        exercisePanel.add(new JLabel(line[0]));
        repsPanel.add(new JLabel(line[1]));
        setsPanel.add(new JLabel(line[2]));
        weightPanel.add(new JLabel(line[3]));

      }
      reader.close();
      historyFrame.setVisible(true);
    } catch (IOException | SecurityException e){
      throw new RuntimeException(e);
    }

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

  private JButton promptHistory(){
    JButton historyButton = new JButton("HISTORY");
    exerciseInput.add(historyButton);

    return historyButton;
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
