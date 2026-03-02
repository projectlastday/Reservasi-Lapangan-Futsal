/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservasi;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jason
 */
public class MainMenu extends javax.swing.JFrame {

    private boolean isMenuExpanded = true;
    private int xx, xy;
    private CardLayout cardLayout;

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();

        header.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                xx = evt.getX();
                xy = evt.getY();
            }
        });
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                int x = evt.getXOnScreen();
                int y = evt.getYOnScreen();
                MainMenu.this.setLocation(x - xx, y - xy);
            }
        });

        setupPages();
        setupNavigation();
    }

    private void setupPages() {
        cardLayout = new CardLayout();
        dashboardview.setLayout(cardLayout);

        JPanel homePage = new JPanel();
        homePage.setBackground(new Color(255, 255, 255));
        JLabel welcomeLabel = new JLabel("Selamat Datang di Futsal Kasir");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(30, 41, 59));
        homePage.add(welcomeLabel);
        dashboardview.add(homePage, "HOME");

        TambahReservasiPanel reservasiPage = new TambahReservasiPanel();
        dashboardview.add(reservasiPage, "TAMBAH_RESERVASI");

        CustomerPanel customerPage = new CustomerPanel();
        dashboardview.add(customerPage, "CUSTOMER");

        InfoReservasiPanel infoReservasiPage = new InfoReservasiPanel();
        dashboardview.add(infoReservasiPage, "INFO_RESERVASI");

        cardLayout.show(dashboardview, "HOME");
    }

    private void setupNavigation() {
        menuhide.setLayout(new BoxLayout(menuhide, BoxLayout.Y_AXIS));

        JPanel btnHome = createNavButton("Dashboard", "HOME");
        JPanel btnReservasi = createNavButton("Tambah Reservasi", "TAMBAH_RESERVASI");
        JPanel btnInfoReservasi = createNavButton("Info Reservasi Hari Ini", "INFO_RESERVASI");
        JPanel btnCustomer = createNavButton("Customer", "CUSTOMER");

        menuhide.add(btnHome);
        menuhide.add(btnReservasi);
        menuhide.add(btnInfoReservasi);
        menuhide.add(btnCustomer);
        menuhide.add(javax.swing.Box.createVerticalGlue());

        // Set initial active state
        setActiveNav(btnHome);
    }

    private void setActiveNav(JPanel activeBtn) {
        for (java.awt.Component c : menuhide.getComponents()) {
            if (c instanceof JPanel) {
                JPanel btn = (JPanel) c;
                JPanel indicator = (JPanel) btn.getComponent(0);
                JLabel label = (JLabel) btn.getComponent(1);

                if (btn == activeBtn) {
                    btn.putClientProperty("isActive", true);
                    btn.setBackground(new Color(226, 232, 240));
                    indicator.setBackground(new Color(59, 130, 246)); // Accent Blue
                    label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    label.setForeground(new Color(15, 23, 42));
                } else {
                    btn.putClientProperty("isActive", false);
                    btn.setBackground(new Color(241, 245, 249));
                    indicator.setBackground(new Color(241, 245, 249));
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    label.setForeground(new Color(51, 65, 85));
                }
            }
        }
    }

    private JPanel createNavButton(String text, String pageName) {
        JPanel btn = new JPanel();
        btn.setLayout(new java.awt.BorderLayout());
        btn.setBackground(new Color(241, 245, 249));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(0.0f);
        btn.setAlignmentY(0.0f);

        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(4, 50));
        indicator.setBackground(new Color(241, 245, 249));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setForeground(new Color(51, 65, 85));
        label.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 10));

        btn.add(indicator, java.awt.BorderLayout.WEST);
        btn.add(label, java.awt.BorderLayout.CENTER);

        btn.putClientProperty("isActive", false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!(Boolean) btn.getClientProperty("isActive")) {
                    btn.setBackground(new Color(226, 232, 240));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!(Boolean) btn.getClientProperty("isActive")) {
                    btn.setBackground(new Color(241, 245, 249));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setActiveNav(btn);
                cardLayout.show(dashboardview, pageName);
            }
        });

        return btn;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        close = new javax.swing.JPanel();
        buttonclose = new javax.swing.JLabel();
        buttonmax = new javax.swing.JPanel();
        fullmax = new javax.swing.JLabel();
        buttonmin = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        menuicon = new javax.swing.JPanel();
        hidemenu = new javax.swing.JPanel();
        buttonhidemenu = new javax.swing.JLabel();
        menuhide = new javax.swing.JPanel();
        dashboardview = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        header.setBackground(new java.awt.Color(226, 232, 240));
        header.setPreferredSize(new java.awt.Dimension(800, 50));
        header.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(226, 232, 240));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setBackground(new java.awt.Color(226, 232, 240));
        close.setPreferredSize(new java.awt.Dimension(50, 50));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeMouseExited(evt);
            }
        });
        close.setLayout(new java.awt.BorderLayout());

        buttonclose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        buttonclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/x-lg.png"))); // NOI18N
        close.add(buttonclose, java.awt.BorderLayout.CENTER);

        jPanel4.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, -1, -1));

        buttonmax.setBackground(new java.awt.Color(226, 232, 240));
        buttonmax.setPreferredSize(new java.awt.Dimension(50, 50));
        buttonmax.setLayout(new java.awt.BorderLayout());

        fullmax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fullmax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/fullscreen.png"))); // NOI18N
        fullmax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fullmaxMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fullmaxMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fullmaxMouseExited(evt);
            }
        });
        buttonmax.add(fullmax, java.awt.BorderLayout.CENTER);

        jPanel4.add(buttonmax, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        buttonmin.setBackground(new java.awt.Color(226, 232, 240));
        buttonmin.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout buttonminLayout = new javax.swing.GroupLayout(buttonmin);
        buttonmin.setLayout(buttonminLayout);
        buttonminLayout.setHorizontalGroup(
            buttonminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        buttonminLayout.setVerticalGroup(
            buttonminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel4.add(buttonmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        header.add(jPanel4, java.awt.BorderLayout.LINE_END);

        getContentPane().add(header, java.awt.BorderLayout.PAGE_START);

        menu.setPreferredSize(new java.awt.Dimension(250, 450));
        menu.setLayout(new java.awt.BorderLayout());

        menuicon.setBackground(new java.awt.Color(203, 213, 225));
        menuicon.setForeground(new java.awt.Color(204, 204, 204));
        menuicon.setPreferredSize(new java.awt.Dimension(50, 450));
        menuicon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hidemenu.setBackground(new java.awt.Color(203, 213, 225));
        hidemenu.setPreferredSize(new java.awt.Dimension(50, 50));
        hidemenu.setLayout(new java.awt.BorderLayout());

        buttonhidemenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        buttonhidemenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/chevron-left.png"))); // NOI18N
        buttonhidemenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonhidemenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonhidemenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonhidemenuMouseExited(evt);
            }
        });
        hidemenu.add(buttonhidemenu, java.awt.BorderLayout.CENTER);

        menuicon.add(hidemenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        menu.add(menuicon, java.awt.BorderLayout.LINE_START);

        menuhide.setBackground(new java.awt.Color(241, 245, 249));

        javax.swing.GroupLayout menuhideLayout = new javax.swing.GroupLayout(menuhide);
        menuhide.setLayout(menuhideLayout);
        menuhideLayout.setHorizontalGroup(
            menuhideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        menuhideLayout.setVerticalGroup(
            menuhideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        menu.add(menuhide, java.awt.BorderLayout.CENTER);

        getContentPane().add(menu, java.awt.BorderLayout.LINE_START);

        dashboardview.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout dashboardviewLayout = new javax.swing.GroupLayout(dashboardview);
        dashboardview.setLayout(dashboardviewLayout);
        dashboardviewLayout.setHorizontalGroup(
            dashboardviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        dashboardviewLayout.setVerticalGroup(
            dashboardviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        getContentPane().add(dashboardview, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 800, 500);
    }// </editor-fold>//GEN-END:initComponents

    public void changecolor(javax.swing.JComponent component, java.awt.Color color) {
        component.setBackground(color);
    }
    
    private void closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseEntered
        changecolor(close, new Color(239, 68, 68));
    }//GEN-LAST:event_closeMouseEntered

    private void closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseExited
        changecolor(close, new Color(226, 232, 240));
    }//GEN-LAST:event_closeMouseExited

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void fullmaxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fullmaxMouseEntered
        changecolor(buttonmax, new Color(203, 213, 225));
    }//GEN-LAST:event_fullmaxMouseEntered

    private void fullmaxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fullmaxMouseExited
        changecolor(buttonmax, new Color(226, 232, 240));
    }//GEN-LAST:event_fullmaxMouseExited

    private void fullmaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fullmaxMouseClicked
        if(this.getExtendedState()!= MainMenu.MAXIMIZED_BOTH){
            this.setExtendedState(MainMenu.MAXIMIZED_BOTH);
        }
        else {
            this.setExtendedState(MainMenu.NORMAL);
        }
    }//GEN-LAST:event_fullmaxMouseClicked

    private void buttonhidemenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonhidemenuMouseEntered
        changecolor(hidemenu, new Color(148, 163, 184));
    }//GEN-LAST:event_buttonhidemenuMouseEntered

    private void buttonhidemenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonhidemenuMouseExited
        changecolor(hidemenu, new Color(203, 213, 225));
    }//GEN-LAST:event_buttonhidemenuMouseExited

    private void buttonhidemenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonhidemenuMouseClicked
        if (isMenuExpanded) {
            menuhide.setVisible(false);
            menu.setPreferredSize(new java.awt.Dimension(50, menu.getHeight()));
            buttonhidemenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/list.png")));
        } else {
            menuhide.setVisible(true);
            menu.setPreferredSize(new java.awt.Dimension(250, menu.getHeight()));
            buttonhidemenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/chevron-left.png")));
        }
        isMenuExpanded = !isMenuExpanded;
        menu.revalidate();
        menu.repaint();
    }//GEN-LAST:event_buttonhidemenuMouseClicked
    
   
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel buttonclose;
    private javax.swing.JLabel buttonhidemenu;
    private javax.swing.JPanel buttonmax;
    private javax.swing.JPanel buttonmin;
    private javax.swing.JPanel close;
    private javax.swing.JPanel dashboardview;
    private javax.swing.JLabel fullmax;
    private javax.swing.JPanel header;
    private javax.swing.JPanel hidemenu;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menuhide;
    private javax.swing.JPanel menuicon;
    // End of variables declaration//GEN-END:variables
}
