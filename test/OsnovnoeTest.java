package test;

import MyPach.Osnovnoe;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OsnovnoeTest {
    // lewenstain
    @org.junit.jupiter.api.Test
    void lewenstain() {
        assertEquals(1, Osnovnoe.lewenstain("Число", "число"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainBigger() {
        assertEquals(9, Osnovnoe.lewenstain("Большое Число", "число"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainLess() {
        assertEquals(7, Osnovnoe.lewenstain("Число", "Молое число"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainExtendedRegister() {
        assertEquals(0, Osnovnoe.lewenstainExtended("ЧИСЛО", "число"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainExtendedOtherSymbols() {
        assertEquals(0, Osnovnoe.lewenstainExtended("Число", "чи с.ло\\)"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainContainsPerfect() {
        assertEquals(0, Osnovnoe.lewenshtainContains("Эта строка содержит Число", "Число"));
    }
    @org.junit.jupiter.api.Test
    void lewenstainContainsLitle() {
        assertEquals(2, Osnovnoe.lewenshtainContains("Число может быть с ошибками", "ис1ло"));
    }

    // isDateInTimeRange
    @org.junit.jupiter.api.Test
    void isDateInTimeRangeOutOfRangeEarly() {
        Osnovnoe.year_start = 2023;
        Osnovnoe.year_end = 2024;

        assertEquals(false, Osnovnoe.isDateInTimeRange("2022-01-09"));
    }
    @org.junit.jupiter.api.Test
    void isDateInTimeRangeOutOfRangeLater() {
        Osnovnoe.year_start = 2023;
        Osnovnoe.year_end = 2024;

        assertEquals(true, Osnovnoe.isDateInTimeRange("2023-12-30"));
    }
    @org.junit.jupiter.api.Test
    void isDateInTimeRangeInRangeSpringEnd() {
        Osnovnoe.year_start = 2023;
        Osnovnoe.year_end = 2024;

        assertEquals(true, Osnovnoe.isDateInTimeRange("2024-02-05"));
    }
    @org.junit.jupiter.api.Test
    void isDateInTimeRangeInRangeSpringStart() {
        Osnovnoe.year_start = 2023;
        Osnovnoe.year_end = 2024;

        assertEquals(false, Osnovnoe.isDateInTimeRange("2023-02-01"));
    }
    @org.junit.jupiter.api.Test
    void isDateInTimeRangeInRangeFall() {
        Osnovnoe.year_start = 2023;
        Osnovnoe.year_end = 2024;

        assertEquals(true, Osnovnoe.isDateInTimeRange("2023-09-01"));
    }

    // remainOnlyWords // а так же цифры
    @org.junit.jupiter.api.Test
    void remainOnlyWordsNumbers() {
        assertEquals("20230901", Osnovnoe.remainOnlyWords("2023-09-01"));
    }
    @org.junit.jupiter.api.Test
    void remainOnlyWordsSymbols() {
        assertEquals("", Osnovnoe.remainOnlyWords("/\n\b\s\t\\-!(~-_-)!"));
    }
    @org.junit.jupiter.api.Test
    void remainOnlyWords() {
        assertEquals("число", Osnovnoe.remainOnlyWords("/ч\nи\bс\sл\tо\\-!(~-_-)!"));
    }

    // chechEmail
    @org.junit.jupiter.api.Test
    void chechEmail() {
        assertEquals(null, Osnovnoe.chechEmail("/ч\nи\bс\sл\tо\\-!(~-_-)!"));
    }
    @org.junit.jupiter.api.Test
    void chechEmail2() {
        assertEquals("dream@gmail.com", Osnovnoe.chechEmail("dream@gmail.com"));
    }
    @org.junit.jupiter.api.Test
    void chechEmail4() {
        assertEquals("dream@gmail.com", Osnovnoe.chechEmail("asf dream@gmail.com\nasd"));
    }
}