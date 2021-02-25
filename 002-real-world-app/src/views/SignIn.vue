<template>
  <v-card class="mx-auto">
    <v-card-title>Sign In</v-card-title>
    <!--  -->
    <app-validation-errors
      v-if="validationErrors"
      :validation-errors="validationErrors"
    ></app-validation-errors>
    <!--  -->
    <v-card-text>
      <v-text-field label="Email" v-model="email"></v-text-field>
      <v-text-field
        label="Password"
        v-model="password"
        :type="showPassword ? 'text' : 'password'"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="primary" @click="onSubmit" :disabled="isSubmitting"
        >Submit</v-btn
      >
    </v-card-actions>
  </v-card>
</template>

<script>
import AppValidationErrors from '@/components/ValidationErrors'

import { actionTypes } from '@/store/modules/auth'
import { mapState } from 'vuex'

export default {
  name: 'AppSignIn',
  components: {
    AppValidationErrors,
  },
  data() {
    return {
      showPassword: false,
      email: '',
      password: '',
    }
  },
  computed: {
    ...mapState({
      validationErrors: (state) => state.auth.validationErrors,
      isSubmitting: (state) => state.auth.isSubmitting,
    }),
  },
  methods: {
    onSubmit() {
      const credentials = {
        email: this.email,
        password: this.password,
      }
      this.$store.dispatch(actionTypes.signin, credentials).then(() => {
        this.$router.push({ name: 'home' })
      })
    },
  },
}
</script>
