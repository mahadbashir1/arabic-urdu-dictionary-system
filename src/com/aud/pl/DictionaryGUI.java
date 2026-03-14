package com.aud.pl;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.aud.bll.IRootBO;
import com.aud.bll.IWordBO;
import com.aud.bll.IMetadataBO;
import com.aud.bll.IPatternBO;
import com.aud.facade.BOFactory;

public class DictionaryGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private RootCrudPanel rootCrudPanel;
    private WordCrudPanel wordCrudPanel;
    private MetadataCrudPanel metadataCrudPanel;
    private PatternCrudPanel patternCrudPanel;

    private IRootBO rootBO;
    private IWordBO wordBO;
    private IMetadataBO metadataBO;
    private IPatternBO patternBO;

    public DictionaryGUI() {
        setTitle("Arabic–Urdu Dictionary");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 247, 250));

        rootBO = BOFactory.getRootBO();
        wordBO = BOFactory.getWordBO();
        metadataBO = BOFactory.getMetadataBO();
        patternBO = BOFactory.getPatternBO();

        setBorder();

        JPanel navPanel = createNavPanel();

        setupCardPanel();

        add(navPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setBorder() {
        JPanel borderPanel = new JPanel();
        borderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Arabic–Urdu Dictionary System",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(25, 25, 112)
        ));
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navPanel.setBackground(new Color(240, 242, 245));

        JButton rootButton = createStyledButton("Root");
        JButton wordButton = createStyledButton("Word");
        JButton metadataButton = createStyledButton("Metadata");
        JButton patternButton = createStyledButton("Pattern");

        navPanel.add(rootButton);
        navPanel.add(wordButton);
        navPanel.add(metadataButton);
        navPanel.add(patternButton);

        rootButton.addActionListener(e -> cardLayout.show(cardPanel, "Root"));
        wordButton.addActionListener(e -> cardLayout.show(cardPanel, "Word"));
        metadataButton.addActionListener(e -> cardLayout.show(cardPanel, "Metadata"));
        patternButton.addActionListener(e -> cardLayout.show(cardPanel, "Pattern"));

        return navPanel;
    }

    private void setupCardPanel() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        rootCrudPanel = new RootCrudPanel(rootBO);
        wordCrudPanel = new WordCrudPanel(wordBO, rootBO);
        metadataCrudPanel = new MetadataCrudPanel(metadataBO, wordBO);
        patternCrudPanel = new PatternCrudPanel(patternBO);

        cardPanel.add(rootCrudPanel, "Root");
        cardPanel.add(wordCrudPanel, "Word");
        cardPanel.add(metadataCrudPanel, "Metadata");
        cardPanel.add(patternCrudPanel, "Pattern");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        new DictionaryGUI();
    }
}