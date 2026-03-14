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

import com.aud.bll.IRootBO;
import com.aud.dtos.RootDTO;

public class RootCrudPanel extends JPanel {
    private JTextField rootField, updateRootField;
    private JComboBox<RootDTO> rootComboBox;
    private IRootBO rootBO;

    public RootCrudPanel(IRootBO rootBO) {
        this.rootBO = rootBO;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Root Management",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(25, 25, 112)
        ));

        JPanel inputPanel = createInputPanel("Add Root");
        rootField = createStyledTextField(15);
        rootField.setToolTipText("Enter Arabic root letters (e.g., كتب)");
        JButton addRootButton = createStyledButton("Add");
        addRootButton.addActionListener(e -> {
            try {
                String rootLetters = rootField.getText().trim();
                if (rootLetters.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Root letters cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                RootDTO rootDTO = new RootDTO();
                rootDTO.setRootLetters(rootLetters);
                rootBO.addRoot(rootDTO);
                JOptionPane.showMessageDialog(this, "Root added: " + rootLetters, "Success", JOptionPane.INFORMATION_MESSAGE);
                rootField.setText("");
                updateRootComboBox();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding root: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        inputPanel.add(new JLabel("Root Letters:"));
        inputPanel.add(rootField);
        inputPanel.add(addRootButton);

        JPanel actionPanel = createInputPanel("Update/Delete Root");
        updateRootField = createStyledTextField(15);
        updateRootField.setToolTipText("Enter new root letters");
        JButton updateRootButton = createStyledButton("Update");
        JButton deleteRootButton = createStyledButton("Delete");
        JButton viewRootsButton = createStyledButton("View Roots");
        rootComboBox = new JComboBox<>();
        rootComboBox.addActionListener(e -> {
            RootDTO selectedRoot = (RootDTO) rootComboBox.getSelectedItem();
            if (selectedRoot != null) {
                updateRootField.setText(selectedRoot.getRootLetters());
            }
        });
        updateRootComboBox();
        viewRootsButton.addActionListener(e -> updateRootComboBox());
        updateRootButton.addActionListener(e -> {
            try {
                String newRootLetters = updateRootField.getText().trim();
                if (newRootLetters.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "New root letters are required", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                RootDTO selected = (RootDTO) rootComboBox.getSelectedItem();
                if (selected != null) {
                    selected.setRootLetters(newRootLetters);
                    rootBO.updateRoot(selected);
                    JOptionPane.showMessageDialog(this, "Root updated to: " + newRootLetters, "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateRootField.setText("");
                    updateRootComboBox();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a root", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating root: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteRootButton.addActionListener(e -> {
            try {
                RootDTO selected = (RootDTO) rootComboBox.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(this, "Please select a root", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                rootBO.deleteRoot(selected.getId());
                JOptionPane.showMessageDialog(this, "Root deleted: " + selected.getRootLetters(), "Success", JOptionPane.INFORMATION_MESSAGE);
                updateRootField.setText("");
                updateRootComboBox();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting root: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        actionPanel.add(new JLabel("New Root Letters:"));
        actionPanel.add(updateRootField);
        actionPanel.add(updateRootButton);
        actionPanel.add(deleteRootButton);
        actionPanel.add(new JLabel("Select Root:"));
        actionPanel.add(rootComboBox);
        actionPanel.add(viewRootsButton);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
    }

    private void updateRootComboBox() {
        rootComboBox.removeAllItems();
        try {
            List<RootDTO> roots = rootBO.getAllRoots();
            for (RootDTO root : roots) {
                rootComboBox.addItem(root);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching roots: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        field.setBackground(new Color(255, 255, 255));
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