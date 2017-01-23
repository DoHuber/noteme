package de.hdm_stuttgart.huber.itprojekt.shared.report;

import java.util.Vector;

@SuppressWarnings("serial")
public class CustomReport extends Report {

    private Vector<SimpleReport> subReports;

    public CustomReport() {

        subReports = new Vector<>();

    }

    public Vector<SimpleReport> getSubReports() {
        return subReports;
    }

    public SimpleReport getSubReportAt(int index) {
        return subReports.get(index);
    }

    public void addSubReport(SimpleReport r) {
        subReports.add(r);
    }

    @Override
    public String toString() {

        String s;
        s = super.toString();

        for (SimpleReport r : subReports) {
            s = s + r.toString();
        }

        return s;

    }


}
