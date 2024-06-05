import java.util.*;
import java.util.stream.Collectors;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> selectedMolecules = new ArrayList<>();
        Map<Molecule, Molecule> parent = new HashMap<>();
        Map<Molecule, Integer> rank = new HashMap<>();

        // Select the weakest bond strength molecule from each structure and initialize union-find structures
        initializeUnionFind(humanStructures, selectedMolecules, parent, rank);
        initializeUnionFind(diffStructures, selectedMolecules, parent, rank);

        // Create and sort potential bonds
        List<Bond> potentialBonds = createPotentialBonds(selectedMolecules);

        // Kruskal's algorithm to form MST
        for (Bond bond : potentialBonds) {
            Molecule root1 = find(parent, bond.getFrom());
            Molecule root2 = find(parent, bond.getTo());
            if (root1 != root2) {
                serum.add(bond);
                union(parent, rank, root1, root2);
            }
        }

        return serum;
    }

    private List<Molecule> getWeakestMoleculesFromEachStructure() {
        List<Molecule> selectedMolecules = new ArrayList<>();
        for (MolecularStructure structure : humanStructures) {
            selectedMolecules.add(structure.getMoleculeWithWeakestBondStrength());
        }
        for (MolecularStructure structure : diffStructures) {
            selectedMolecules.add(structure.getMoleculeWithWeakestBondStrength());
        }
        return selectedMolecules;
    }

    private List<Bond> generatePossibleBonds(List<Molecule> selectedMolecules) {
        List<Bond> possibleBonds = new ArrayList<>();
        for (int i = 0; i < selectedMolecules.size(); i++) {
            for (int j = i + 1; j < selectedMolecules.size(); j++) {
                double bondStrength = (selectedMolecules.get(i).getBondStrength() + selectedMolecules.get(j).getBondStrength()) / 2.0;
                Molecule molecule1 = selectedMolecules.get(i);
                Molecule molecule2 = selectedMolecules.get(j);
                // Ensure the bond is added with molecules in a consistent order
                if (Integer.parseInt(molecule1.getId().substring(1)) < Integer.parseInt(molecule2.getId().substring(1))) {
                    possibleBonds.add(new Bond(molecule1, molecule2, bondStrength));
                } else {
                    possibleBonds.add(new Bond(molecule2, molecule1, bondStrength));
                }
            }
        }
        return possibleBonds;
    }

    private void initializeMoleculesAndUnionFind(List<Molecule> molecules, List<MolecularStructure> structures, Map<Molecule, Molecule> parent, Map<Molecule, Integer> rank) {
        for (MolecularStructure structure : structures) {
            Molecule molecule = structure.getMoleculeWithWeakestBondStrength();
            if (molecule != null) {
                molecules.add(molecule);
                parent.put(molecule, molecule);
                rank.put(molecule, 0);
            }
        }
    }

    private List<Bond> createAndSortBonds(List<Molecule> molecules) {
        List<Bond> bonds = new ArrayList<>();
        for (int i = 0; i < molecules.size(); i++) {
            for (int j = i + 1; j < molecules.size(); j++) {
                Molecule m1 = molecules.get(i);
                Molecule m2 = molecules.get(j);
                double bondStrength = (m1.getBondStrength() + m2.getBondStrength()) / 2.0;
                bonds.add(new Bond(m1, m2, bondStrength));
            }
        }
        bonds.sort(Comparator.comparingDouble(Bond::getWeight));
        return bonds;
    }

    private boolean union(Map<Molecule, Molecule> parent, Map<Molecule, Integer> rank, Molecule x, Molecule y) {
        Molecule rootX = find(parent, x);
        Molecule rootY = find(parent, y);
        if (rootX != rootY) {
            if (rank.get(rootX) > rank.get(rootY)) {
                parent.put(rootY, rootX);
            } else if (rank.get(rootX) < rank.get(rootY)) {
                parent.put(rootX, rootY);
            } else {
                parent.put(rootY, rootX);
                rank.put(rootX, rank.get(rootX) + 1);
            }
            return true;
        }
        return false;
    }

    private Molecule find(Map<Molecule, Molecule> parent, Molecule molecule) {
        if (molecule != parent.get(molecule)) {
            parent.put(molecule, find(parent, parent.get(molecule)));
        }
        return parent.get(molecule);
    }

    private void initializeUnionFind(List<MolecularStructure> structures, List<Molecule> selectedMolecules, Map<Molecule, Molecule> parent, Map<Molecule, Integer> rank) {
        for (MolecularStructure structure : structures) {
            Molecule molecule = structure.getMoleculeWithWeakestBondStrength();
            if (molecule != null) {
                selectedMolecules.add(molecule);
                parent.put(molecule, molecule);
                rank.put(molecule, 0);
            }
        }
    }

    private List<Bond> createPotentialBonds(List<Molecule> molecules) {
        List<Bond> potentialBonds = new ArrayList<>();
        for (int i = 0; i < molecules.size(); i++) {
            for (int j = i + 1; j < molecules.size(); j++) {
                Molecule m1 = molecules.get(i);
                Molecule m2 = molecules.get(j);
                double bondStrength = (m1.getBondStrength() + m2.getBondStrength()) / 2.0;
                potentialBonds.add(new Bond(m1, m2, bondStrength));
            }
        }
        potentialBonds.sort(Comparator.comparingDouble(Bond::getWeight));
        return potentialBonds;
    }

    private List<Molecule> getWeakestMolecules(List<MolecularStructure> structures) {
        return structures.stream()
                .map(structure -> structure.getMoleculeWithWeakestBondStrength())
                .collect(Collectors.toList());
    }

    private void getAllSelectedMolecules(List<MolecularStructure> structures, List<Molecule> selectedMolecules, Map<Molecule, Molecule> parent, Map<Molecule, Integer> rank) {
        for (MolecularStructure structure : structures) {
            Molecule minMolecule = structure.getMoleculeWithWeakestBondStrength();
            if (minMolecule != null && !parent.containsKey(minMolecule)) {
                selectedMolecules.add(minMolecule);
                parent.put(minMolecule, minMolecule);
                rank.put(minMolecule, 0);
            }
        }
    }



    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + getMoleculeIds(humanStructures));
        System.out.println("Vitales molecules selected for synthesis: " + getMoleculeIds(diffStructures));
        System.out.println("Synthesizing the serum...");
        double totalStrength = 0.0;
        for (Bond bond : serum) {
            Molecule m1 = bond.getFrom();
            Molecule m2 = bond.getTo();
            System.out.printf("Forming a bond between %s - %s with strength %.2f\n", m1.getId(), m2.getId(), bond.getWeight());
            totalStrength += bond.getWeight();
        }
        System.out.printf("The total serum bond strength is %.2f\n", totalStrength);
    }







    private List<Molecule> moleculeGetter(List<MolecularStructure> structures) {
        List<Molecule> moleculesSel = new ArrayList<>();
        for (MolecularStructure structure : structures) {
            Molecule minMolecule = structure.getMoleculeWithWeakestBondStrength();
            if (minMolecule != null) {
                moleculesSel.add(minMolecule);
            }
        }
        return moleculesSel;
    }
    private String getMoleculeIds(List<MolecularStructure> structures) {
        return structures.stream()
                .map(structure -> structure.getMoleculeWithWeakestBondStrength())
                .filter(Objects::nonNull)
                .map(Molecule::getId)
                .sorted()
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
