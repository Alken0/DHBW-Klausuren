module exam {
	requires java.desktop;
	requires jdk.javadoc;
	// you still need to download javadoc yourself
	//intellij: file/project structure/SDKs/<ur_sdk>/documentation_paths

	// delete the following lines + test_lib if testing is not desired
	// otherwise you need to add junit to your classpath (hover on imports in a testfile)
	requires junit;
	exports test_lib;
}
