<template>
  <v-app-bar dense>
    <v-app-bar-nav-icon> </v-app-bar-nav-icon>
    <v-toolbar-title to="/">Real App</v-toolbar-title>
    <v-spacer></v-spacer>

    <template v-if="isAnonimous">
      <v-btn outlined text to="/">Home</v-btn>
      <v-btn outlined text to="/register"
        ><v-icon dense>mdi-login</v-icon>Sign Up</v-btn
      >
      <v-btn outlined text to="/signin"
        ><v-icon dense>mdi-login</v-icon>Sign In</v-btn
      >
    </template>

    <template v-if="isLoggedIn">
      <v-btn outlined text to="/">Home</v-btn>
      <v-btn outlined text :to="{ name: 'createArticle' }">
        <v-icon>mdi-pencil</v-icon>New Article</v-btn
      >
      <v-btn outlined text :to="{ name: 'settings' }"
        ><v-icon>mdi-account-cog </v-icon>Settings</v-btn
      >
      <v-btn
        outlined
        text
        :to="{ name: 'userProfile', params: { slug: currentUser.username } }"
        >{{ currentUser.username }}</v-btn
      >
    </template>
    <!-- <router-link to="/">Home</router-link> -->
  </v-app-bar>
</template>

<script>
import { mapGetters } from 'vuex'

import { getterTypes } from '@/store/modules/auth'

export default {
  name: 'AppNavbar',
  computed: {
    ...mapGetters({
      currentUser: getterTypes.currentUser,
      isAnonimous: getterTypes.isAnonimous,
      isLoggedIn: getterTypes.isLoggedIn,
    }),
  },
}
</script>
