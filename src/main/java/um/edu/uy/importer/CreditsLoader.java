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

    public static void loadCredits(String csvFilePath, HashTable<String, Actor> actorsById, HashTable<String, Participant> participantsById) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (fields.length >= 3) {
                    String movieId = fields [2].trim().replaceAll("\"", "");
                    String castData = fields [0].trim().replaceAll("^\"|\"$", "");
                    String crewData = fields [1].trim().replaceAll("^\"|\"$", "");

                    processCast(castData, movieId, actorsById);
                    processCrew(crewData, movieId, participantsById);

                }

            }

            boolean hayDatosActores = actorsById.pertenece("1") != null || actorsById.pertenece("2") != null;
            boolean hayDatosParticipantes = participantsById.pertenece("1") != null || participantsById.pertenece("2") != null;

            System.out.println("Verificación de carga: Actores (" + (hayDatosActores ? "SÍ" : "NO") +
                    "), Participantes (" + (hayDatosParticipantes ? "SÍ" : "NO") + ")");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void processCast(String cast, String movieId, HashTable<String, Actor> actorsById) {
        if (cast == null || cast.equals("[]")) return;

        int i = 0;
        while ((i = cast.indexOf("{", i)) != -1) {
            int end = cast.indexOf("}", i);
            if (end == -1) break;

            String part = cast.substring(i, end + 1);
            String id = extract(part, "'id': ", ",", "}");
            String name = extract(part, "'name': '", "'", null);

            if (id != null && name != null) {
                Elemento<String, Actor> elemento = actorsById.pertenece(id);
                Actor actor;
                if (elemento == null) {
                    String creditId = extract(part, "'credit_id': '", "'", null);
                    actor = new Actor(id, name, creditId != null ? creditId : "");
                    try {
                        actorsById.insertar(id, actor);
                    } catch (Exception e) {
                    }
                } else {
                    actor = elemento.getValor();
                }

                String castId = extract(part, "\"cast_id\": ", ",", "}" );

                String creditId = extract(part, "'credit_id': '", "'", null);
                CastParticipation participation = new CastParticipation(
                        castId != null ? castId : "",
                        movieId,
                        creditId != null ? creditId : "");

                actor.addCastParticipation(participation);
            }
            i = end + 1;
        }
    }

    private static void processCrew(String crew, String movieId, HashTable<String, Participant> participantsById) {
        if (crew == null || crew.equals("[]")) return;

        int i = 0;
        while ((i = crew.indexOf("{", i)) != -1) {
            int end = crew.indexOf("}", i);
            if (end == -1) break;

            String part = crew.substring(i, end + 1);
            String id = extract(part, "\"id\": ", ",", "}");
            String name = extract(part, "\"name\": \"", "\"", null);

            if (id != null && name != null) {
                Elemento<String, Participant> elemento = participantsById.pertenece(id);
                Participant participant;
                if (elemento == null) {
                    String creditId = extract(part, "\"credit_id\": \"", "\"", null);
                    participant = new Participant(id, name, creditId != null ? creditId : "");
                    try {
                        participantsById.insertar(id, participant);
                    } catch (Exception e) {
                    }
                } else {
                    participant = elemento.getValor();
                }

                String job = extract(part, "\"job\": \"", "\"", null);
                CrewParticipation participation = new CrewParticipation(job != null ? job : "");
                participant.addCrewParticipation(participation);
            }
            i = end + 1;
        }
    }
    private static String extract(String text, String key, String end1, String end2) {
        int start = text.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = end1 != null ? text.indexOf(end1, start) : -1;
        if (end == -1 && end2 != null) end = text.indexOf(end2, start);
        if (end == -1) return text.substring(start).trim();
        return text.substring(start, end).trim();
    }
}