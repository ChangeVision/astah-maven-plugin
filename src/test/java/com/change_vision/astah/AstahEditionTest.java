package com.change_vision.astah;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AstahEditionTest {

    @Test
    public void getJARName_editionがprofessionalの場合astah_pro_jarを返すこと() {
        final String name = AstahEdition.professional.getJARName();
        assertThat(name, is("astah-pro.jar"));
    }

    @Test
    public void getJARName_editionがcommunityの場合astah_community_jarを返すこと() {
        final String name = AstahEdition.community.getJARName();
        assertThat(name, is("astah-community.jar"));
    }

    @Test
    public void getJARName_editionがumlの場合astah_uml_jarを返すこと() {
        final String name = AstahEdition.uml.getJARName();
        assertThat(name, is("astah-uml.jar"));
    }

    @Test
    public void getJARName_editionがsysmlの場合astah_sys_jarを返すこと() {
        final String name = AstahEdition.sysml.getJARName();
        assertThat(name, is("astah-sys.jar"));
    }

    @Test
    public void getJARName_editionがsafetyの場合astahsystemsafety_jarを返すこと() {
        final String name = AstahEdition.safety.getJARName();
        assertThat(name, is("astahsystemsafety.jar"));
    }

}
