package com.example.eksamensprojekt.midlertidigeKlasser;


// Klasse til at oprette de 30 kunstværker fra Watanabe-Samlingen
public class ImporterKunstværker {
    /*
    public static void main(String[] args) throws Exception {

        DAO dao = new DAOImplementation();

        ArrayList<Kunstværk> kunstværker = new ArrayList<>();

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 1",
                "",
                "Jesus ofrer sig selv",
                "Sadao Watanabe",
                1961,
                "80x69 cm",
                "70x58 cm",
                "Billedet fremstiller den lidende Jesus umiddelbart før hans tilfangetagelse og korsfæstelse i Getsemane Have.",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/1.jpg")),
                1,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 2",
                "",
                "Jesu indtog i Jerusalem",
                "Sadao Watanabe",
                1962,
                "95x82 cm",
                "85x62 cm",
                "Billedet fremstiller Jesu indtog i Jerusalem palmesøndag. Billedet er kompositorisk opdelt med tilhængerne af Jesus i den øverste del og modstanderne i den nederste del.",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/2.jpg")),
                4,
                false
                ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 3",
                "",
                "Jesus vasker disciplenes fødder",
                "Sadao Watanabe",
                1962,
                "83x72 cm",
                "71x61 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/3.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 4",
                "",
                "Flugten til Ægypten",
                "Sadao Watanabe",
                1962,
                "80x68 cm",
                "69x58 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/4.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 5",
                "",
                "Den helige nadver",
                "Sadao Watanabe",
                1966,
                "86x72 cm",
                "75x62 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/5.jpg")),
                1,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 6",
                "",
                "Hyrderne modtager budskabet fra Gud",
                "Sadao Watanabe",
                1966,
                "67x77 cm",
                "60x66 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/6.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 7",
                "",
                "De tre vise mænd tilbeder Jesus i stalden",
                "Sadao Watanabe",
                1968,
                "83x72 cm",
                "71x61 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/7.jpg")),
                3,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 8",
                "",
                "Korset",
                "Sadao Watanabe",
                1970,
                "79x66 cm",
                "68x57 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/8.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 9",
                "",
                "Bjergprædiken",
                "Sadao Watanabe",
                1981,
                "73x86 cm",
                "62x78 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/9.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 10",
                "",
                "De tre vise mænd på vej hjem",
                "Sadao Watanabe",
                1972,
                "92x71 cm",
                "81x63 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/10.jpg")),
                2,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 11",
                "",
                "Kristi Himmelfartsdag",
                "Sadao Watanabe",
                1981,
                "73x81 cm",
                "62x70 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/11.jpg")),
                2,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 12",
                "",
                "Jesus i Emmaus",
                "Sadao Watanabe",
                1981,
                "80x68 cm",
                "58x67 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/12.jpg")),
                2,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 13",
                "",
                "Flugten til Ægypten",
                "Sadao Watanabe",
                1981,
                "68x75 cm",
                "58x66 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/13.jpg")),
                2,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 14",
                "",
                "Stormen på søen",
                "Sadao Watanabe",
                1981,
                "83x72 cm",
                "73x63 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/14.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 15",
                "Tryk 21 ud af 100",
                "Jesu lidelse og død",
                "Sadao Watanabe",
                1981,
                "77x70 cm",
                "68x60 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/15.jpg")),
                1,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 16",
                "Tryk 82 ud af 100",
                "Palmesøndag",
                "Sadao Watanabe",
                1982,
                "103x76 cm",
                "92x68 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/16.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 17",
                "Tryk 39 ud af 100",
                "Kanans druer",
                "Sadao Watanabe",
                1983,
                "73x80 cm",
                "62x71 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/17.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 18",
                "Tryk 33 ud af 100",
                "Hyrderne i stalden",
                "Sadao Watanabe",
                1984,
                "83x72 cm",
                "71x62 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/18.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 19",
                "Tryk 33 ud af 100",
                "Kristus og den syge",
                "Sadao Watanabe",
                1984,
                "81x69 cm",
                "70x60 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/19.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 20",
                "Tryk 46 ud af 100",
                "Brylluppet i Kanan",
                "Sadao Watanabe",
                1984,
                "50x80 cm",
                "62x70 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/20.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 21",
                "Tryk 32 ud af 100",
                "De tre vise mænds ankomst",
                "Sadao Watanabe",
                1984,
                "72x80 cm",
                "61x70 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/21.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 22",
                "Tryk 41 ud af 100",
                "Disciplenes fiskefangst",
                "Sadao Watanabe",
                1984,
                "73x60 cm",
                "62x49 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/22.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 23",
                "Tryk 77 ud af 100",
                "Englen forhindrer Bileam i at rejse den forkerte vej",
                "Sadao Watanabe",
                1986,
                "72x83 cm",
                "62x73 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/23.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 25",
                "Tryk 21 ud af 100",
                "De tre vise mænd",
                "Sadao Watanabe",
                1987,
                "80x68 cm",
                "67x59 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/25.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 26",
                "Tryk 47 ud af 100",
                "Flugten",
                "Sadao Watanabe",
                1987,
                "80x72 cm",
                "71x62 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/26.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 27",
                "Tryk 88 ud af 100",
                "Madonna",
                "Sadao Watanabe",
                1988,
                "73x68 cm",
                "62x49 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/27.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 28",
                "",
                "Lille pige samler vagtler",
                "Sadao Watanabe",
                1972,
                "81x70 cm",
                "69x61 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/28.jpg")),
                3,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 29",
                "Tryk 80 ud af 100",
                "Jesu fødsel - jul",
                "Sadao Watanabe",
                1982,
                "73x58 cm",
                "63x49 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/29.jpg")),
                3,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 30",
                "Tryk 10 ud af 100",
                "Besøget (Maria og Elisabeth)",
                "Sadao Watanabe",
                1988,
                "83x73 cm",
                "71x61 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/30.jpg")),
                3,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 31",
                "Tryk 15 ud af 100",
                "Fodvaskningen",
                "Sadao Watanabe",
                1989,
                "83x73 cm",
                "70x63 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/31.jpg")),
                4,
                false
        ));

        kunstværker.add(new Kunstværk(
                "MH 1991/1365. 32",
                "Tryk 2 ud af 100",
                "Den sjette basun",
                "Sadao Watanabe",
                1989,
                "83x73 cm",
                "70x62 cm",
                "",
                Files.readAllBytes(Path.of("/Users/emma/Desktop/KUNSTHAL HOLMEN/Watanabe_Samlingen/32.jpg")),
                4,
                false
        ));

        for (Kunstværk kunstværk : kunstværker) {
            dao.gemKunstværk(kunstværk);
        }
    }
     */
}