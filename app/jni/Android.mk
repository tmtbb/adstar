LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := secrecy
LOCAL_SRC_FILES := basictypes.cpp data_packet.cpp packet_process_assist.cpp packet_process_function.cpp

LOCAL_LDLIBS += -lz
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)