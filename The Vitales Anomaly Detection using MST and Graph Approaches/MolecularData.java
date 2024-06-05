import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }






    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        for (Molecule molecule : molecules) {
            if (!visited.getOrDefault(molecule.getId(), false)) {
                MolecularStructure structure = new MolecularStructure();
                dfs(molecule, visited, structure);
                structures.add(structure);
            }
        }
        return structures;
    }

    // DFS method to explore from a given molecule
    private void dfs(Molecule molecule, Map<String, Boolean> visited, MolecularStructure structure) {
        // Mark the current node as visited and add to structure
        visited.put(molecule.getId(), true);
        structure.addMolecule(molecule);

        // Recur for all the vertices adjacent to this vertex
        for (String bond : molecule.getBonds()) {
            if (!visited.getOrDefault(bond, false)) {
                Molecule bondedMolecule = findMoleculeById(bond);
                if (bondedMolecule != null) {
                    dfs(bondedMolecule, visited, structure);
                }
            }
        }
    }
    // Helper method to find a molecule by ID
    private Molecule findMoleculeById(String id) {
        for (Molecule m : molecules) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }









    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        int count = 1;
        for (MolecularStructure structure : molecularStructures) {
            System.out.println("Molecules in Molecular Structure " + count + ": " + structure);
            count++;
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        Set<MolecularStructure> sourceSet = new HashSet<>(sourceStructures);

        for (MolecularStructure targetStructure : targetStructures) {
            if (!sourceSet.contains(targetStructure)) {
                anomalyList.add(targetStructure);
            }
        }
        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure);
        }
    }
}
