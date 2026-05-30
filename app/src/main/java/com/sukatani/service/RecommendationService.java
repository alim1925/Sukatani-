package com.sukatani.service;

import com.sukatani.model.SensorData;
import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    public static class Recommendation {
        private String condition;
        private String suggestion;
        private String severity; // info, warning, danger

        public Recommendation(String condition, String suggestion, String severity) {
            this.condition = condition;
            this.suggestion = suggestion;
            this.severity = severity;
        }

        public String getCondition() { return condition; }
        public String getSuggestion() { return suggestion; }
        public String getSeverity() { return severity; }
    }

    public List<Recommendation> generateRecommendations(SensorData data) {
        List<Recommendation> list = new ArrayList<>();
        if (data == null) return list;

        // Rules from gemini.md
        if (data.getSoilMoisture() < 30) {
            list.add(new Recommendation("Tanah terlalu kering (" + String.format("%.1f", data.getSoilMoisture()) + "%)", "Siram 5 liter air", "danger"));
        }

        if (data.getTemperature() > 35) {
            list.add(new Recommendation("Suhu terlalu tinggi (" + String.format("%.1f", data.getTemperature()) + "°C)", "Tambahkan naungan", "warning"));
        }

        if (data.getPh() < 5.5) {
            list.add(new Recommendation("Tanah terlalu asam (pH " + String.format("%.1f", data.getPh()) + ")", "Tambahkan dolomit", "warning"));
        } else if (data.getPh() > 7.5) {
            list.add(new Recommendation("Tanah terlalu basa (pH " + String.format("%.1f", data.getPh()) + ")", "Tambahkan kompos", "warning"));
        }

        if (list.isEmpty()) {
            list.add(new Recommendation("Kondisi Optimal", "Pertahankan perawatan rutin", "info"));
        }

        return list;
    }
}
