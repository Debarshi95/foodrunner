#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_dev_foodrunner_di_app_1scope_NetworkModule_getApiKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "f8c2b313b379b1");
}

JNIEXPORT jstring JNICALL
Java_com_example_dev_foodrunner_ui_MainActivity_getApiKey(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "f8c2b313b379b1");
}