package um.edu.uy.importer;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Participant;
import um.edu.uy.entities.CastParticipation;
import um.edu.uy.entities.CrewParticipation;
import um.edu.uy.tads.hash.HashTable;
import um.edu.uy.tads.hash.Elemento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreditsLoader {

    @SuppressWarnings("unchecked")
    public static void loadCredits(String csvFilePath, HashTable<String, Actor> actorsById, HashTable<String, Participant> participantsById) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);

                if (parts.length >= 3) {
                    String castData = parts[0];
                    String crewData = parts[1];
                    String movieId = parts[2];

                    processCast(castData, movieId, actorsById);
                    processCrew(crewData, movieId, participantsById);
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando créditos: " + e.getMessage());
        }
    }

    private static String[] parseCSVLine(String line) {
        if (line.contains("\t")) {
            return line.split("\t", -1);
        } else {
            return splitCSVWithQuotes(line);
        }
    }

    private static String[] splitCSVWithQuotes(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    private static void processCast(String cast, String movieId, HashTable<String, Actor> actorsById) {
        if (cast == null || cast.equals("[]") || cast.trim().isEmpty()) return;

        int i = 0;
        while ((i = cast.indexOf("'id': ", i)) != -1) {
            int start = i + 6;
            int end = cast.indexOf(",", start);
            if (end == -1) end = cast.indexOf("}", start);
            if (end == -1) break;

            String id = cast.substring(start, end).trim();

            int nameStart = cast.indexOf("'name': '", i);
            if (nameStart != -1) {
                nameStart += 9;
                int nameEnd = cast.indexOf("'", nameStart);
                if (nameEnd != -1) {
                    String name = cast.substring(nameStart, nameEnd);

                    Elemento<String, Actor> elemento = actorsById.pertenece(id);
                    Actor actor;

                    if (elemento == null) {
                        actor = new Actor(id, name, "");
                        try {
                            actorsById.insertar(id, actor);
                        } catch (Exception e) {
                        }
                    } else {
                        actor = elemento.getValor();
                    }

                    CastParticipation participation = new CastParticipation("", movieId, "");
                    actor.addCastParticipation(participation);
                }
            }

            i = end + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private static void processCrew(String crew, String movieId, HashTable<String, Participant> participantsById) {
        if (crew == null || crew.equals("[]") || crew.trim().isEmpty()) return;

        int i = 0;
        while ((i = crew.indexOf("'id': ", i)) != -1) {
            int start = i + 6;
            int end = crew.indexOf(",", start);
            if (end == -1) end = crew.indexOf("}", start);
            if (end == -1) break;

            String id = crew.substring(start, end).trim();

            int nameStart = crew.indexOf("'name': '", i);
            if (nameStart != -1) {
                nameStart += 9;
                int nameEnd = crew.indexOf("'", nameStart);
                if (nameEnd != -1) {
                    String name = crew.substring(nameStart, nameEnd);

                    String job = "";
                    int jobStart = crew.indexOf("'job': '", i);
                    if (jobStart != -1) {
                        jobStart += 8;
                        int jobEnd = crew.indexOf("'", jobStart);
                        if (jobEnd != -1) {
                            job = crew.substring(jobStart, jobEnd);
                        }
                    }

                    Elemento<String, Participant> elemento = participantsById.pertenece(id);
                    Participant participant;

                    if (elemento == null) {
                        participant = new Participant(id, name, "");
                        try {
                            participantsById.insertar(id, participant);
                        } catch (Exception e) {
                        }
                    } else {
                        participant = elemento.getValor();
                    }

                    CrewParticipation participation = new CrewParticipation(job);
                    participant.addCrewParticipation(participation);
                }
            }

            i = end + 1;
        }
    }
}