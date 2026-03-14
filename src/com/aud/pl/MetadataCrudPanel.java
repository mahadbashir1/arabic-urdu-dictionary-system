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

import com.aud.bll.IMetadataBO;
import com.aud.bll.IWordBO;
import com.aud.dtos.MetadataDTO;
import com.aud.dtos.WordDTO;

public class MetadataCrudPanel extends JPanel {
    private JComboBox<WordDTO> wordComboBox;
    private JTextField genderField, numberField, caseField, verbFormField, tenseField, transitivityField;
    private IMetadataBO metadataBO;
    private IWordBO wordBO;

    private JComboBox<WordDTO> updateWordComboBox;
    private JTextField updateGenderField, updateNumberField, updateCaseField, updateVerbFormField, updateTenseField, updateTransitivityField;

    public MetadataCrudPanel(IMetadataBO metadataBO, IWordBO wordBO) {
        this.metadataBO = metadataBO;
        this.wordBO = wordBO;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Metadata Management",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(25, 25, 112)
        ));

        JPanel inputPanel = createInputPanel("Add Metadata");
        wordComboBox = new JComboBox<>();
        genderField = createStyledTextField(10);
        numberField = createStyledTextField(10);
        caseField = createStyledTextField(10);
        verbFormField = createStyledTextField(10);
        tenseField = createStyledTextField(10);
        transitivityField = createStyledTextField(10);

        JButton addMetadataButton = createStyledButton("Add");
        addMetadataButton.addActionListener(e -> addMetadata());

        inputPanel.add(new JLabel("Word:"));
        inputPanel.add(wordComboBox);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderField);
        inputPanel.add(new JLabel("Number:"));
        inputPanel.add(numberField);
        inputPanel.add(new JLabel("Case:"));
        inputPanel.add(caseField);
        inputPanel.add(new JLabel("Verb Form:"));
        inputPanel.add(verbFormField);
        inputPanel.add(new JLabel("Tense:"));
        inputPanel.add(tenseField);
        inputPanel.add(new JLabel("Transitivity:"));
        inputPanel.add(transitivityField);
        inputPanel.add(addMetadataButton);

        JPanel actionPanel = createInputPanel("Update/Delete Metadata");
        updateWordComboBox = new JComboBox<>();
        updateWordComboBox.addActionListener(e -> loadSelectedMetadata());
        updateGenderField = createStyledTextField(10);
        updateNumberField = createStyledTextField(10);
        updateCaseField = createStyledTextField(10);
        updateVerbFormField = createStyledTextField(10);
        updateTenseField = createStyledTextField(10);
        updateTransitivityField = createStyledTextField(10);

        JButton updateMetadataButton = createStyledButton("Update");
        JButton deleteMetadataButton = createStyledButton("Delete");
        JButton viewWordsButton = createStyledButton("View Words");
        viewWordsButton.addActionListener(e -> updateWordComboBoxes());
        updateMetadataButton.addActionListener(e -> updateMetadata());
        deleteMetadataButton.addActionListener(e -> deleteMetadata());

        actionPanel.add(new JLabel("Select Word:"));
        actionPanel.add(updateWordComboBox);
        actionPanel.add(new JLabel("Gender:"));
        actionPanel.add(updateGenderField);
        actionPanel.add(new JLabel("Number:"));
        actionPanel.add(updateNumberField);
        actionPanel.add(new JLabel("Case:"));
        actionPanel.add(updateCaseField);
        actionPanel.add(new JLabel("Verb Form:"));
        actionPanel.add(updateVerbFormField);
        actionPanel.add(new JLabel("Tense:"));
        actionPanel.add(updateTenseField);
        actionPanel.add(new JLabel("Transitivity:"));
        actionPanel.add(updateTransitivityField);
        actionPanel.add(updateMetadataButton);
        actionPanel.add(deleteMetadataButton);
        actionPanel.add(viewWordsButton);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);

        updateWordComboBoxes();
    }

    private void updateWordComboBoxes() {
        wordComboBox.removeAllItems();
        updateWordComboBox.removeAllItems();
        try {
            List<WordDTO> words = wordBO.getAllWords();
            for (WordDTO w : words) {
                wordComboBox.addItem(w);
                updateWordComboBox.addItem(w);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading words: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMetadata() {
        try {
            WordDTO word = (WordDTO) wordComboBox.getSelectedItem();
            if (word == null) {
                JOptionPane.showMessageDialog(this, "Select a word", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MetadataDTO dto = new MetadataDTO();
            dto.setWordId(word.getId());
            dto.setGender(genderField.getText().trim());
            dto.setNumber(numberField.getText().trim());
            dto.setCaseType(caseField.getText().trim());
            dto.setVerbForm(verbFormField.getText().trim());
            dto.setTense(tenseField.getText().trim());
            dto.setTransitivity(transitivityField.getText().trim());
            metadataBO.addMetadata(dto);
            JOptionPane.showMessageDialog(this, "Metadata added", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearAddFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedMetadata() {
        WordDTO selected = (WordDTO) updateWordComboBox.getSelectedItem();
        if (selected != null) {
            try {
                MetadataDTO meta = metadataBO.getMetadataByWordId(selected.getId());
                if (meta != null) {
                    updateGenderField.setText(meta.getGender());
                    updateNumberField.setText(meta.getNumber());
                    updateCaseField.setText(meta.getCaseType());
                    updateVerbFormField.setText(meta.getVerbForm());
                    updateTenseField.setText(meta.getTense());
                    updateTransitivityField.setText(meta.getTransitivity());
                } else {
                    clearUpdateFields();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading metadata", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMetadata() {
        try {
            WordDTO selected = (WordDTO) updateWordComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a word", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MetadataDTO dto = new MetadataDTO();
            dto.setWordId(selected.getId());
            dto.setGender(updateGenderField.getText().trim());
            dto.setNumber(updateNumberField.getText().trim());
            dto.setCaseType(updateCaseField.getText().trim());
            dto.setVerbForm(updateVerbFormField.getText().trim());
            dto.setTense(updateTenseField.getText().trim());
            dto.setTransitivity(updateTransitivityField.getText().trim());
            metadataBO.updateMetadata(dto);
            JOptionPane.showMessageDialog(this, "Metadata updated", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMetadata() {
        try {
            WordDTO selected = (WordDTO) updateWordComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a word", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            metadataBO.deleteMetadata(selected.getId());
            JOptionPane.showMessageDialog(this, "Metadata deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearUpdateFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAddFields() {
        genderField.setText("");
        numberField.setText("");
        caseField.setText("");
        verbFormField.setText("");
        tenseField.setText("");
        transitivityField.setText("");
    }

    private void clearUpdateFields() {
        updateGenderField.setText("");
        updateNumberField.setText("");
        updateCaseField.setText("");
        updateVerbFormField.setText("");
        updateTenseField.setText("");
        updateTransitivityField.setText("");
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