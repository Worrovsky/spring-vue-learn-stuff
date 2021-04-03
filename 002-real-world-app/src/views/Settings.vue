<template>
  <v-container v-if="currentUser">
    <v-row>
      <v-col>
        <h1>Settings</h1>
        <app-validation-errors
          v-if="validationErrors"
          :validation-errors="validationErrors"
        />

        <v-text-field label="Image" v-model="form.image"></v-text-field>
        <v-text-field label="Username" v-model="form.username"></v-text-field>
        <v-textarea label="Bio" v-model="form.bio"></v-textarea>
        <v-text-field label="Email" v-model="form.email"></v-text-field>
        <v-text-field
          label="Password"
          type="password"
          v-model="form.password"
        ></v-text-field>

        <div class="mx-auto d-flex">
          <v-spacer></v-spacer>
          <v-btn
            x-large
            color="primary"
            @click="onSubmit"
            :disabled="isSubmitting"
            >Update Settings</v-btn
          >
        </div>

        <v-btn @click="onLogout" color="error">Logout</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import {
  getterTypes as authGetterTypes,
  actionTypes as authActionTypes,
} from '@/store/modules/auth'

import AppValidationErrors from '@/components/ValidationErrors'

export default {
  name: 'AppSettings',
  components: {
    AppValidationErrors,
  },
  computed: {
    ...mapState({
      isSubmitting: (state) => state.settings.isSubmitting,
      validationErrors: (state) => state.settings.validationErrors,
    }),
    ...mapGetters({
      currentUser: authGetterTypes.currentUser,
    }),
    form() {
      if (this.currentUser) {
        return {
          username: this.currentUser.username,
          bio: this.currentUser.bio,
          image: this.currentUser.image,
          email: this.currentUser.email,
          password: '',
        }
      }
      return {
        username: '',
        bio: '',
        image: '',
        email: '',
        password: '',
      }
    },
  },
  methods: {
    onSubmit() {
      this.$store.dispatch(authActionTypes.updateCurrentUser, {
        currentUserInput: this.form,
      })
    },
    onLogout() {
      this.$store.dispatch(authActionTypes.logout).then(() => {
        this.$router.push({ name: 'home' })
      })
    },
  },
}
</script>
