package com.change_vision.astah;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.StringStartsWith.startsWith;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class CommandBuilderTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void editionがnullの場合例外が発生すること() {
		Set<String> jvmProp = new HashSet<String>();
		AstahEdition edition = null;
		new CommandBuilder(jvmProp , edition);
	}
	
	@Test(expected=IllegalStateException.class)
	public void もしプロパティファイルが取得できなかった場合はISEが投げられること() throws Exception {
		CommandBuilder builder = new CommandBuilder(new HashSet<String>(), AstahEdition.safilia){
			@Override
			protected InputStream getDevPropertiesResource() {
				return null;
			}
		};
		builder.build();
	}
	
	@Test(expected=IllegalStateException.class)
	public void もしプロパティファイル取得中にIOExceptionが発生した場合はISEが投げられること() throws Exception {
		CommandBuilder builder = new CommandBuilder(new HashSet<String>(), AstahEdition.safilia){
			@Override
			protected InputStream getDevPropertiesResource() {
				InputStream stream = mock(InputStream.class);
				IOException ex = mock(IOException.class);
				try {
					when(stream.read((byte[])anyObject())).thenThrow(ex);
				} catch (IOException e) {
				}
				return stream;
			}
		};
		builder.build();
	}
	
	@Test
	public void jvmPropに特に指定がなくても出力されること() throws Exception {
		CommandBuilder builder = new CommandBuilder(null, AstahEdition.safilia);
		List<String> commands = builder.build();
		assertThat(commands.get(0),is("java"));
		assertThat(commands.get(1),is(startsWith("-Dplugin.switch.file")));
		assertThat(commands.get(2),is("-jar"));
		assertThat(commands.get(3),is("safilia.jar"));
		assertThat(commands.get(4),is("-clean"));
	}

	@Test
	public void jvmPropに指定がある場合追加されて出力されること() throws Exception {
		HashSet<String> jvmProp = new HashSet<String>();
		jvmProp.add("-Djava.language=en");
		CommandBuilder builder = new CommandBuilder(jvmProp, AstahEdition.safilia);
		List<String> commands = builder.build();
		assertThat(commands.get(0),is("java"));
		assertThat(commands.get(1),is("-Djava.language=en"));
		assertThat(commands.get(2),is(startsWith("-Dplugin.switch.file")));
		assertThat(commands.get(3),is("-jar"));
		assertThat(commands.get(4),is("safilia.jar"));
		assertThat(commands.get(5),is("-clean"));
	}
	
}
