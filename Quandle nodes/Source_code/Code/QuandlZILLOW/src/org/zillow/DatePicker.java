package org.zillow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DatePicker {

	public static void main(String[] args) {
		JLabel label = new JLabel("Selected Date:");
		final JTextField text = new JTextField(10);
		JButton b = new JButton("popup");
		JPanel p = new JPanel();
		p.add(label);
		p.add(text);
		p.add(b);
		final JFrame f = new JFrame();
//		f.setPreferredSize(new Dimension(300, 220));
		f.getContentPane().add(p);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				text.setText(new DatePicker().setPickedDate());
			}
		});
	}

	int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
	JLabel l = new JLabel("", JLabel.CENTER);
	String day = "";
	JDialog d;
	JButton[] button = new JButton[49];

	public DatePicker() {
		d = new JDialog();
		d.setPreferredSize(new Dimension(550, 300));
		d.setModal(true);
		

		
		String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.setPreferredSize(new Dimension(350, 220));

		for (int x = 0; x < button.length; x++) {
			final int selection = x;
			button[x] = new JButton();
			button[x].setFocusPainted(false);
			button[x].setBackground(Color.white);
			if (x > 6) {
				button[x].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						day = button[selection].getActionCommand();
						d.dispose();
					}
				});
			}
			if (x < 7) {
				button[x].setText(header[x]);
				button[x].setForeground(Color.red);
			}
			p1.add(button[x]);
		}
		JPanel p2 = new JPanel(new GridLayout(1, 1));

		// Last year button
		JButton lastYear = new JButton("<< Last Year");
		lastYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				year--;
				displayDate();
			}
		});
		p2.add(lastYear);

		// Previous month button
		JButton previous = new JButton("<< Previous");
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				month--;
				displayDate();
			}
		});
		p2.add(previous);

		// Selected Date @ popup
		p2.add(l);

		// Next month button
		JButton next = new JButton("Next >>");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				month++;
				displayDate();
			}
		});
		p2.add(next);

		// Next year button
		JButton nextYear = new JButton("Next Year >>");
		nextYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				year++;
				displayDate();
			}
		});
		p2.add(nextYear);

//		d.add(p0, BorderLayout.CENTER);
		d.add(p1, BorderLayout.CENTER);
		d.add(p2, BorderLayout.SOUTH);
		d.pack();
//		d.setLocationRelativeTo(parent);
		displayDate();
		d.setVisible(true);
	}

	public void displayDate() {
		for (int x = 7; x < button.length; x++) {
			button[x].setText("");
		}

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, 1);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) {
			button[x].setText("" + day);
		}

		l.setText(sdf.format(cal.getTime()));
		d.setTitle("Date Picker");
	}

	public String setPickedDate() {
		if (day.equals("")) {
			return day;
		}

		// Set the return date format
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, Integer.parseInt(day));
		return sdf.format(cal.getTime());
	}
}