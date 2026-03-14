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

import com.aud.bll.IPatternBO;
import com.aud.dtos.PatternDTO;

public class PatternCrudPanel extends JPanel {
    private JTextField patternField, updatePatternField;
    private JComboBox<PatternDTO> patternComboBox;
    private IPatternBO patternBO;

    public PatternCrudPanel(IPatternBO patternBO) {
        this.patternBO = patternBO;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Pattern Management",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(25, 25, 112)
        ));

        JPanel inputPanel = createInputPanel("Add Pattern");
        patternField = createStyledTextField(20);
        patternField.setToolTipText("Enter pattern form (e.g., فَعَلَ)");
        JButton addPatternButton = createStyledButton("Add");
        addPatternButton.addActionListener(e -> {
            try {
                String form = patternField.getText().trim();
                if (form.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pattern form cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PatternDTO dto = new PatternDTO();
                dto.setPatternForm(form);
                patternBO.addPattern(dto);
                JOptionPane.showMessageDialog(this, "Pattern added: " + form, "Success", JOptionPane.INFORMATION_MESSAGE);
                patternField.setText("");
                updatePatternComboBox();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding pattern: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputPanel.add(new JLabel("Pattern Form:"));
        inputPanel.add(patternField);
        inputPanel.add(addPatternButton);

        JPanel actionPanel = createInputPanel("Update/Delete Pattern");
        updatePatternField = createStyledTextField(20);
        updatePatternField.setToolTipText("Enter new pattern form");
        JButton updatePatternButton = createStyledButton("Update");
        JButton deletePatternButton = createStyledButton("Delete");
        JButton viewPatternsButton = createStyledButton("View All");
        patternComboBox = new JComboBox<>();
        patternComboBox.addActionListener(e -> {
            PatternDTO selected = (PatternDTO) patternComboBox.getSelectedItem();
            if (selected != null) {
                updatePatternField.setText(selected.getPatternForm());
            }
        });
        updatePatternComboBox();
        viewPatternsButton.addActionListener(e -> updatePatternComboBox());

        updatePatternButton.addActionListener(e -> {
            try {
                String newForm = updatePatternField.getText().trim();
                if (newForm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "New pattern form required", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PatternDTO selected = (PatternDTO) patternComboBox.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(this, "Select a pattern", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selected.setPatternForm(newForm);
                patternBO.updatePattern(selected);
                JOptionPane.showMessageDialog(this, "Pattern updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                updatePatternField.setText("");
                updatePatternComboBox();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deletePatternButton.addActionListener(e -> {
            try {
                PatternDTO selected = (PatternDTO) patternComboBox.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(this, "Select a pattern", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                patternBO.deletePattern(selected.getId());
                JOptionPane.showMessageDialog(this, "Pattern deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                updatePatternField.setText("");
                updatePatternComboBox();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        actionPanel.add(new JLabel("Select Pattern:"));
        actionPanel.add(patternComboBox);
        actionPanel.add(new JLabel("New Form:"));
        actionPanel.add(updatePatternField);
        actionPanel.add(updatePatternButton);
        actionPanel.add(deletePatternButton);
        actionPanel.add(viewPatternsButton);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);

        updatePatternComboBox();
    }

    private void updatePatternComboBox() {
        patternComboBox.removeAllItems();
        try {
            List<PatternDTO> patterns = patternBO.getAllPatterns();
            for (PatternDTO p : patterns) {
                patternComboBox.addItem(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading patterns: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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