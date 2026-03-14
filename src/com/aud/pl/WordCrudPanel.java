package com.aud.pl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.aud.bll.IWordBO;
import com.aud.bll.IRootBO;
import com.aud.dtos.WordDTO;
import com.aud.dtos.RootDTO;

public class WordCrudPanel extends JPanel {
    private JTextField arabicField, baseField, urduField, posField;
    private JComboBox<RootDTO> rootComboBox;
    private JComboBox<WordDTO> wordComboBox;
    private IWordBO wordBO;
    private IRootBO rootBO;

    private JTextField updateArabicField, updateBaseField, updateUrduField, updatePosField;
    private JComboBox<RootDTO> updateRootComboBox;

    public WordCrudPanel(IWordBO wordBO, IRootBO rootBO) {
        this.wordBO = wordBO;
        this.rootBO = rootBO;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Word Management",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(25, 25, 112)
        ));

        JPanel inputPanel = createInputPanel("Add Word");
        arabicField = createStyledTextField(15);
        baseField = createStyledTextField(15);
        urduField = createStyledTextField(20);
        posField = createStyledTextField(10);
        rootComboBox = new JComboBox<>();

        updateArabicField = createStyledTextField(15);
        updateBaseField = createStyledTextField(15);
        updateUrduField = createStyledTextField(20);
        updatePosField = createStyledTextField(10);
        updateRootComboBox = new JComboBox<>();  // Initialize BEFORE loadRoots()
        wordComboBox = new JComboBox<>();

        // Now safe to call loadRoots()
        loadRoots();

        JButton addWordButton = createStyledButton("Add");
        addWordButton.addActionListener(e -> addWord());

        inputPanel.add(new JLabel("Arabic Form:"));
        inputPanel.add(arabicField);
        inputPanel.add(new JLabel("Base Form:"));
        inputPanel.add(baseField);
        inputPanel.add(new JLabel("Urdu Meaning:"));
        inputPanel.add(urduField);
        inputPanel.add(new JLabel("Part of Speech:"));
        inputPanel.add(posField);
        inputPanel.add(new JLabel("Root:"));
        inputPanel.add(rootComboBox);
        inputPanel.add(addWordButton);

        JPanel actionPanel = createInputPanel("Update/Delete Word");
        wordComboBox.addActionListener(e -> loadSelectedWord());

        JButton updateWordButton = createStyledButton("Update");
        JButton deleteWordButton = createStyledButton("Delete");
        JButton viewWordsButton = createStyledButton("View All");
        viewWordsButton.addActionListener(e -> updateWordComboBox());
        updateWordButton.addActionListener(e -> updateWord());
        deleteWordButton.addActionListener(e -> deleteWord());

        actionPanel.add(new JLabel("Select Word:"));
        actionPanel.add(wordComboBox);
        actionPanel.add(new JLabel("Arabic:"));
        actionPanel.add(updateArabicField);
        actionPanel.add(new JLabel("Base:"));
        actionPanel.add(updateBaseField);
        actionPanel.add(new JLabel("Urdu:"));
        actionPanel.add(updateUrduField);
        actionPanel.add(new JLabel("POS:"));
        actionPanel.add(updatePosField);
        actionPanel.add(new JLabel("Root:"));
        actionPanel.add(updateRootComboBox);
        actionPanel.add(updateWordButton);
        actionPanel.add(deleteWordButton);
        actionPanel.add(viewWordsButton);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);

        updateWordComboBox();
    }

    private void loadRoots() {
        rootComboBox.removeAllItems();
        updateRootComboBox.removeAllItems();
        try {
            List<RootDTO> roots = rootBO.getAllRoots();
            for (RootDTO r : roots) {
                rootComboBox.addItem(r);
                updateRootComboBox.addItem(r);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading roots: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addWord() {
        try {
            String arabic = arabicField.getText().trim();
            String base = baseField.getText().trim();
            String urdu = urduField.getText().trim();
            String pos = posField.getText().trim();
            RootDTO root = (RootDTO) rootComboBox.getSelectedItem();
            if (arabic.isEmpty() || urdu.isEmpty() || pos.isEmpty() || root == null) {
                JOptionPane.showMessageDialog(this, "All fields required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            WordDTO dto = new WordDTO();
            dto.setArabicForm(arabic);
            dto.setBaseForm(base);
            dto.setUrduMeaning(urdu);
            dto.setPartOfSpeech(pos);
            dto.setRootId(root.getId());
            wordBO.addWord(dto);
            JOptionPane.showMessageDialog(this, "Word added", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearAddFields();
            updateWordComboBox();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateWordComboBox() {
        wordComboBox.removeAllItems();
        try {
            List<WordDTO> words = wordBO.getAllWords();
            for (WordDTO w : words) {
                wordComboBox.addItem(w);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading words: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedWord() {
        WordDTO selected = (WordDTO) wordComboBox.getSelectedItem();
        if (selected != null) {
            updateArabicField.setText(selected.getArabicForm());
            updateBaseField.setText(selected.getBaseForm());
            updateUrduField.setText(selected.getUrduMeaning());
            updatePosField.setText(selected.getPartOfSpeech());
            try {
                RootDTO root = rootBO.getRootById(selected.getRootId());
                if (root != null) {
                    updateRootComboBox.setSelectedItem(root);
                } else {
                    updateRootComboBox.setSelectedIndex(-1);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading root", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateWord() {
        try {
            WordDTO selected = (WordDTO) wordComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a word", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String arabic = updateArabicField.getText().trim();
            String base = updateBaseField.getText().trim();
            String urdu = updateUrduField.getText().trim();
            String pos = updatePosField.getText().trim();
            RootDTO root = (RootDTO) updateRootComboBox.getSelectedItem();
            if (arabic.isEmpty() || urdu.isEmpty() || pos.isEmpty() || root == null) {
                JOptionPane.showMessageDialog(this, "All fields required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            selected.setArabicForm(arabic);
            selected.setBaseForm(base);
            selected.setUrduMeaning(urdu);
            selected.setPartOfSpeech(pos);
            selected.setRootId(root.getId());
            wordBO.updateWord(selected);
            JOptionPane.showMessageDialog(this, "Word updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateWordComboBox();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteWord() {
        try {
            WordDTO selected = (WordDTO) wordComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a word", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            wordBO.deleteWord(selected.getId());
            JOptionPane.showMessageDialog(this, "Word deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateWordComboBox();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAddFields() {
        arabicField.setText("");
        baseField.setText("");
        urduField.setText("");
        posField.setText("");
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(65, 105, 225));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
        });
        return button;
    }

    private JPanel createInputPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
            title,
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            new Color(25, 25, 112)
        ));
        return panel;
    }
}