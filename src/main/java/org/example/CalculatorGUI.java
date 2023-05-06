package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class CalculatorGUI {
    private static final String[] SYSTEMS = {"2", "8", "16"};

    private final JFrame window;
    private final JTextField numField;
    private final JTextField resultField;
    private final JComboBox<String> systemComboBox;

    public CalculatorGUI() {
        window = new JFrame("Калькулятор");
        window.setSize(1000, 1000);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel enterLabel = new JLabel("Введите число:");
        numField = new JTextField();
        JLabel systemLabel = new JLabel("Выберите систему счисления:");
        systemComboBox = new JComboBox<>(SYSTEMS);
        JButton calcButton = new JButton("Вычислить");
        JLabel resultLabel = new JLabel("Результат:");
        resultField = new JTextField();
        JLabel historyLabel = new JLabel("История перевода чисел:");
        JTextArea historyTextArea = new JTextArea(); // Инициализация компонента JTextArea
        JScrollPane scrollPane = new JScrollPane(historyTextArea); // Добавление прокручиваемой панели

        JLabel loglabel = new JLabel("Логические операции");
        JLabel enterOne = new JLabel("<html>Введите первое число (если вы решили использовать<br> операцию NOT, то необходимо вводить число в первое поле)<html>:");
        JTextField num1 = new JTextField();

        JLabel enterTwo = new JLabel("Введите второе число:");
        JTextField num2 = new JTextField();

        JLabel logres = new JLabel("Результат:");
        JTextField logresult = new JTextField();


        //Размещение компонентов для перевода
        enterLabel.setBounds(1, 0, 100, 20);
        numField.setBounds(1, 30, 100, 20);
        systemLabel.setBounds(1, 35, 300, 75);
        systemComboBox.setBounds(1, 90, 100, 20);
        calcButton.setBounds(1, 130, 100, 40);
        resultLabel.setBounds(1, 160, 300, 75);
        resultField.setBounds(1, 210, 100, 20);
        historyLabel.setBounds(600, 0, 200, 20);
        scrollPane.setBounds(600, 30, 220, 280);


        //Добвление кнопок для логических операций
        JButton andButton = new JButton("AND");
        JButton orButton = new JButton("OR");
        JButton xorButton = new JButton("XOR");
        JButton notButton = new JButton("NOT");


        //Размещение компонентов для логических выраженний
        loglabel.setBounds(350,0,200,20);

        enterOne.setBounds(320,30,200,65);
        num1.setBounds(365,100,100,20);

        enterTwo.setBounds(350,120,200,20);
        num2.setBounds(365,150,100,20);

        logres.setBounds(380,290,200,20);
        logresult.setBounds(365,310,100,20);

        andButton.setBounds(330, 180, 70, 40);
        orButton.setBounds(430, 180, 70, 40);
        xorButton.setBounds(330, 240, 70, 40);
        notButton.setBounds(430, 240, 70, 40);

        //Слушатель для кнопки перевода
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputNum = numField.getText();
                String selectedSystem = (String) systemComboBox.getSelectedItem();
                try {
                    // Ограничение на 20 последних записей
                    FileReader fileReader = new FileReader("history.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    ArrayList<String> lines = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        if (lines.size() < 19) {
                            lines.add(line);
                        } else {
                            lines.remove(lines.size() - 19);
                            lines.add(line);
                        }
                    }
                    bufferedReader.close();
                    String result = Calculator.transform(inputNum, selectedSystem);
                    resultField.setText(result);
                    lines.add(FileLogger.logger(inputNum, selectedSystem, result));
                    historyTextArea.setText(String.join("\n", lines));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Неверный формат числа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(window, "Ошибка при записи в файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //Слушатель для кнопки AND
        andButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number1 = num1.getText();
                String number2 = num2.getText();
                try {
                    int op1 = Integer.parseInt(number1);
                    int op2 = Integer.parseInt(number2);
                    int result = op1 & op2;
                    logresult.setText(String.valueOf(result));

                    // Ограничение на 20 последних записей
                    FileReader fileReader = new FileReader("history.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    ArrayList<String> lines = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        if (lines.size() < 19) {
                            lines.add(line);
                        } else {
                            lines.remove(lines.size() - 19);
                            lines.add(line);
                        }
                    }
                    bufferedReader.close();

                    // Добавление новой записи в файл и в поле истории
                    FileWriter writer = new FileWriter("history.csv", true);

                    // Добавление данных в файл с использованием разделителя и кавычек
                    String record = String.format("\"%s\";\"%s\";\"%s\";\"%s\"\n", number1, "and" ,number2, result);
                    writer.write(record);
                    writer.close();

                    // Добавление записи в список
                    lines.add(String.format("\"%s\";\"%s\";\"%s\";\"%s\"", number1, "and" ,number2, result));
                    historyTextArea.setText(String.join("\n", lines));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Неверный формат числа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }  catch (IOException ex) {
                    JOptionPane.showMessageDialog(window, "Ошибка при записи в файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

            }
        });

        //Слушатель для кнопки OR
        orButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number1 = num1.getText();
                String number2 = num2.getText();
                try {
                    int op1 = Integer.parseInt(number1);
                    int op2 = Integer.parseInt(number2);
                    int result = op1 | op2;
                    logresult.setText(String.valueOf(result));

                    // Ограничение на 20 последних записей
                    FileReader fileReader = new FileReader("history.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    ArrayList<String> lines = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        if (lines.size() < 19) {
                            lines.add(line);
                        } else {
                            lines.remove(lines.size() - 19);
                            lines.add(line);
                        }
                    }
                    bufferedReader.close();

                    // Добавление новой записи в файл и в поле истории
                    FileWriter writer = new FileWriter("history.csv", true);

                    // Добавление данных в файл с использованием разделителя и кавычек
                    String record = String.format("\"%s\";\"%s\";\"%s\";\"%s\"\n", number1, "or" ,number2, result);
                    writer.write(record);
                    writer.close();

                    // Добавление записи в список
                    lines.add(String.format("\"%s\";\"%s\";\"%s\";\"%s\"", number1, "or" ,number2, result));
                    historyTextArea.setText(String.join("\n", lines));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Неверный формат числа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Слушатель для кнопки XOR
        xorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number1 = num1.getText();
                String number2 = num2.getText();
                try {
                    int op1 = Integer.parseInt(number1);
                    int op2 = Integer.parseInt(number2);
                    int result = op1 ^ op2;
                    logresult.setText(String.valueOf(result));
                    // Ограничение на 20 последних записей
                    FileReader fileReader = new FileReader("history.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    ArrayList<String> lines = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        if (lines.size() < 19) {
                            lines.add(line);
                        } else {
                            lines.remove(lines.size() - 19);
                            lines.add(line);
                        }
                    }
                    bufferedReader.close();

                    // Добавление новой записи в файл и в поле истории
                    FileWriter writer = new FileWriter("history.csv", true);

                    // Добавление данных в файл с использованием разделителя и кавычек
                    String record = String.format("\"%s\";\"%s\";\"%s\";\"%s\"\n", number1, "xor" ,number2, result);
                    writer.write(record);
                    writer.close();

                    // Добавление записи в список
                    lines.add(String.format("\"%s\";\"%s\";\"%s\";\"%s\"", number1, "xor" ,number2, result));
                    historyTextArea.setText(String.join("\n", lines));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Неверный формат числа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Слушатель для кнопки NOT
        notButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = num1.getText();
                try {
                    int op = Integer.parseInt(number);
                    int result = ~op;
                    logresult.setText(String.valueOf(result));
                    // Ограничение на 20 последних записей
                    FileReader fileReader = new FileReader("history.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    ArrayList<String> lines = new ArrayList<>();
                    while ((line = bufferedReader.readLine()) != null) {
                        if (lines.size() < 19) {
                            lines.add(line);
                        } else {
                            lines.remove(lines.size() - 19);
                            lines.add(line);
                        }
                    }
                    bufferedReader.close();

                    // Добавление новой записи в файл и в поле истории
                    FileWriter writer = new FileWriter("history.csv", true);

                    // Добавление данных в файл с использованием разделителя и кавычек
                    String record = String.format("\"%s\";\"%s\";\"%s\"\n", "not" ,number, result);
                    writer.write(record);
                    writer.close();
                    // Добавление записи в список
                    lines.add(String.format("\"%s\";\"%s\";\"%s\"", "not" ,number, result));
                    historyTextArea.setText(String.join("\n", lines));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(window, "Неверный формат числа", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        window.add(enterLabel);
        window.add(numField);
        window.add(systemLabel);
        window.add(systemComboBox);
        window.add(calcButton);
        window.add(resultLabel);
        window.add(resultField);
        window.add(historyLabel);
        window.add(scrollPane);
        historyTextArea.setEditable(false);
        window.setVisible(true);
        window.add(loglabel);
        window.add(enterOne);
        window.add(num1);
        window.add(enterTwo);
        window.add(num2);
        window.add(logres);
        window.add(logresult);
        window.add(andButton);
        window.add(orButton);
        window.add(xorButton);
        window.add(notButton);
    }

}
