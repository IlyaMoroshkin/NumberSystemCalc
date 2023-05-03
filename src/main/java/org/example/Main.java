package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Создание окна программы
        JFrame window = new JFrame("Калькулятор");
        window.setSize(270, 300);
        window.setLayout(null);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Поля для ввода
        JTextField num_Field = new JTextField();
        JTextField result_Field = new JTextField();

        //Текстовые поля
        JLabel enter_Jlabel = new JLabel("Введите число:");
        JLabel sysnum_Jlabel = new JLabel("Выберите систему счисления:");
        JLabel result_Jlabel = new JLabel("Результат:");

        //Элементы раскрывающегося списка
        String[] items = {
                "2",
                "8",
                "16"
        };

        //Раскрывающийся список для выбора системы счисления
        JComboBox sysnum_comboBox = new JComboBox(items);

        //Кнопки программы
        JButton calc = new JButton("Вычислить");
        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getNum = num_Field.getText();

                String getSys = (String) sysnum_comboBox.getSelectedItem();
                try {
                    FileWriter writer = new FileWriter("history.txt", true);

                    Integer number = Integer.parseInt(getNum);

                    int numSys = Integer.parseInt(getSys);

                    writer.write("Число: ");
                    writer.append(number + "\n");
                    writer.write("Система счисления в которую переводим: ");
                    writer.append(numSys + "\n");
                    writer.write("Результат: ");
                    writer.append(transform(number, numSys) + "\n");
                    writer.write("----------\n");
                    writer.close();
                    result_Field.setText(transform(number, numSys));
                } catch (Exception ex) {
                }

            }
        });

        enter_Jlabel.setBounds(1, 0, 100, 20);
        num_Field.setBounds(1, 30, 100, 20);
        sysnum_Jlabel.setBounds(1, 35, 300, 75);
        sysnum_comboBox.setBounds(1, 90, 100, 20);
        calc.setBounds(1, 130, 100, 40);
        result_Jlabel.setBounds(1, 160, 300, 75);
        result_Field.setBounds(1, 210, 100, 20);

        window.add(sysnum_Jlabel);
        window.add(sysnum_comboBox);
        window.add(enter_Jlabel);
        window.add(num_Field);
        window.add(calc);
        window.add(result_Jlabel);
        window.add(result_Field);
        window.setVisible(true);
        window.setResizable(false);
    }

    static String transform(int number, int numSys) {
        return switch (numSys) {
            case 2 -> Integer.toBinaryString(number);
            case 8 -> Integer.toOctalString(number);
            case 16 -> Integer.toHexString(number);
            default -> throw new IllegalStateException("Ошибка, введите 2, 8 или 16" + numSys);
        };
    }
}